package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.exception.PdfTextExtractException;
import com.avenga.fil.lt.model.FileType;
import com.avenga.fil.lt.model.LineContent;
import com.avenga.fil.lt.model.Pages;
import com.avenga.fil.lt.service.ImagePdfExtractingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.avenga.fil.lt.constant.GeneralConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImagePdfExtractingServiceImpl implements ImagePdfExtractingService {

    private static final Integer IMAGE_DPI = 300;

    private final TextractClient textractClient;
    private final S3Client s3Client;

    @Override
    public Pages extractTextFormImage(String bucketName, String key) {
        log.info(EXTRACTING_FROM_IMAGE);
        var textResponse = textractClient.detectDocumentText(prepareDocumentRequest(bucketName, key));
        return new Pages(List.of(parseAndPrepareLinesContent(textResponse.blocks())));
    }

    @Override
    public Pages extractTextFromPdf(String bucketName, String key) {
        log.info(EXTRACTING_FROM_PDF);
        var inputDocument = generateInputDocument(bucketName, key);
        var pdfRenderer = new PDFRenderer(inputDocument);
        return new Pages(IntStream.range(0, inputDocument.getNumberOfPages())
                .mapToObj(pageIndex -> dividePdfDocument(pageIndex, pdfRenderer))
                .collect(Collectors.toList()));
    }

    private PDDocument generateInputDocument(String bucketName, String key) {
        try {
            var fullObject = s3Client.getObjectAsBytes(GetObjectRequest.builder().bucket(bucketName).key(key).build());
            return PDDocument.load(fullObject.asInputStream());
        } catch (IOException e) {
            throw new PdfTextExtractException(LOADING_PDF_ERROR);
        }
    }

    private List<LineContent> dividePdfDocument(int pageIndex, PDFRenderer pdfRenderer) {
        try {
            var image = pdfRenderer.renderImageWithDPI(pageIndex, IMAGE_DPI, ImageType.RGB);
            var byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIOUtil.writeImage(image, FileType.JPEG.toString(), byteArrayOutputStream);
            var textResponse = textractClient.detectDocumentText(prepareDocumentRequest(byteArrayOutputStream));
            return parseAndPrepareLinesContent(textResponse.blocks());
        } catch (IOException e) {
            throw new PdfTextExtractException(TEXT_EXTRACT_PDF_ERROR);
        }
    }

    private DetectDocumentTextRequest prepareDocumentRequest(String bucketName, String key) {
        return DetectDocumentTextRequest.builder().document(Document.builder()
                .s3Object(S3Object.builder().bucket(bucketName).name(key).build()).build()).build();
    }

    private DetectDocumentTextRequest prepareDocumentRequest(ByteArrayOutputStream outputStream) {
        return DetectDocumentTextRequest.builder().document(
                Document.builder().bytes(SdkBytes.fromByteArray(outputStream.toByteArray())).build()).build();
    }

    private List<LineContent> parseAndPrepareLinesContent(List<Block> blocks) {
        return blocks.stream().filter(b -> b.blockType().equals(BlockType.LINE))
                .map(b -> LineContent.builder().text(b.text())
                        .yAxis(b.geometry().polygon().get(0).y())
                        .xAxis(b.geometry().polygon().get(0).x()).build()).collect(Collectors.toList());
    }
}

