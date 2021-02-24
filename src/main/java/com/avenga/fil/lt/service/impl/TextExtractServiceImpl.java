package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.model.FileType;
import com.avenga.fil.lt.model.RequestPayloadData;
import com.avenga.fil.lt.model.TextTranslateInput;
import com.avenga.fil.lt.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

import static com.avenga.fil.lt.constant.GeneralConstant.TEXT_EXTRACT_LAMBDA_SUCCESS;
import static com.avenga.fil.lt.constant.GeneralConstant.TEXT_TRANSLATION_LAMBDA_INVOKED;

@Slf4j
@Service
public class TextExtractServiceImpl implements TextExtractService {

    private final TextTranslateService textTranslateService;
    private final RequestParserService requestParserService;
    private final ObjectMapper objectMapper;
    private final ResponseService responseService;
    private final Map<FileType, Function<RequestPayloadData, Object>> actionResolver;

    public TextExtractServiceImpl(ImagePdfExtractingService imagePdfExtractingService,
                                  ExcelExtractingService excelExtractingService,
                                  TxtExtractingService txtExtractingService, TextTranslateService textTranslateService,
                                  RequestParserService requestParserService, ObjectMapper objectMapper,
                                  ResponseService responseService) {
        this.textTranslateService = textTranslateService;
        this.requestParserService = requestParserService;
        this.objectMapper = objectMapper;
        this.responseService = responseService;
        this.actionResolver = Map.of(
            FileType.PDF, imagePdfExtractingService::extractTextFromPdf,
            FileType.JPG, imagePdfExtractingService::extractTextFormImage,
            FileType.JPEG, imagePdfExtractingService::extractTextFormImage,
            FileType.PNG, imagePdfExtractingService::extractTextFormImage,
            FileType.BMP, imagePdfExtractingService::extractTextFormImage,
            FileType.XLS, excelExtractingService::extractTextFromXls,
            FileType.XLSX, excelExtractingService::extractTextFromXlsx,
            FileType.TXT, txtExtractingService::extractTextFromTxt
        );
    }

    @Override
    public Object processRequest(Map<String, String> request) {
        try {
            var data = requestParserService.parseAndPreparePayload(request);
            var pages = actionResolver.get(FileType.valueOf(data.getFileType().toUpperCase()))
                    .apply(data);
            invokingTranslateTextProcess(pages, data);
            log.info(TEXT_EXTRACT_LAMBDA_SUCCESS);
            return pages;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return responseService.createErrorResponse(exception);
        }
    }

    private void invokingTranslateTextProcess(Object content, RequestPayloadData data) throws JsonProcessingException {
        textTranslateService.translate(TextTranslateInput.builder()
                .fileKey(data.getFileKey())
                .sourceLanguage(data.getSourceLanguage())
                .targetLanguage(data.getTargetLanguage())
                .userId(data.getUserId())
                .documentName(data.getDocumentName())
                .fileType(data.getFileType())
                .text(objectMapper.writeValueAsString(content))
                .build());
        log.info(TEXT_TRANSLATION_LAMBDA_INVOKED);
    }
}
