package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.exception.XlsReadingException;
import com.avenga.fil.lt.exception.XlsxReadingException;
import com.avenga.fil.lt.model.RequestPayloadData;
import com.avenga.fil.lt.service.ExcelExtractingService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.avenga.fil.lt.constant.GeneralConstant.XLSX_READING_ERROR;
import static com.avenga.fil.lt.constant.GeneralConstant.XLS_READING_ERROR;

@Service
@RequiredArgsConstructor
public class ExcelExtractingServiceImpl implements ExcelExtractingService {

    private final S3Client s3Client;

    @Override
    public List<List<String>> extractTextFromXls(RequestPayloadData data) {
        try {
            var workbook = new HSSFWorkbook(documentStream(data.getBucketName(), data.getFileKey()));
            return readText(workbook.getSheetAt(0));
        } catch (Exception e) {
            throw new XlsReadingException(String.format(XLS_READING_ERROR, e.getMessage()));
        }
    }

    @Override
    public List<List<String>> extractTextFromXlsx(RequestPayloadData data) {
        try {
            Workbook workbook = new XSSFWorkbook(documentStream(data.getBucketName(), data.getFileKey()));
            return readText(workbook.getSheetAt(0));
        } catch (IOException e) {
            throw new XlsxReadingException(String.format(XLSX_READING_ERROR, e.getMessage()));
        }
    }

    private List<List<String>> readText(Sheet sheet) {
        List<List<String>> cellMatrix = new ArrayList<>(sheet.getLastRowNum());
        var formatter = new DataFormatter();
        sheet.rowIterator().forEachRemaining(r -> { cellMatrix.add(r.getRowNum(), new ArrayList<>());
            r.cellIterator().forEachRemaining(
                    cell -> cellMatrix.get(cell.getRowIndex()).add(formatter.formatCellValue(cell)));
        });
        return cellMatrix.stream().filter(list -> !list.isEmpty()).collect(Collectors.toList());
    }

    private InputStream documentStream(String bucketName, String key) {
        return s3Client.getObjectAsBytes(GetObjectRequest.builder().bucket(bucketName).key(key).build()).asInputStream();
    }
}
