package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.exception.AbsentRequiredParameter;
import com.avenga.fil.lt.model.FileType;
import com.avenga.fil.lt.service.ExcelExtractingService;
import com.avenga.fil.lt.service.ImagePdfExtractingService;
import com.avenga.fil.lt.service.TextExtractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.avenga.fil.lt.constant.ApiEventConstants.*;
import static com.avenga.fil.lt.constant.GeneralConstant.ABSENT_REQUIRED_PARAMETER;
import static com.avenga.fil.lt.constant.GeneralConstant.TEXT_EXTRACT_LAMBDA_SUCCESS;

@Slf4j
@Service
public class TextExtractServiceImpl implements TextExtractService {

    private final Map<FileType, BiFunction<String, String, Object>> actionResolver;

    public TextExtractServiceImpl(ImagePdfExtractingService imagePdfExtractingService,
                                  ExcelExtractingService excelExtractingService) {
        this.actionResolver = Map.of(
            FileType.PDF, imagePdfExtractingService::extractTextFromPdf,
            FileType.JPG, imagePdfExtractingService::extractTextFormImage,
            FileType.JPEG, imagePdfExtractingService::extractTextFormImage,
            FileType.PNG, imagePdfExtractingService::extractTextFormImage,
            FileType.BMP, imagePdfExtractingService::extractTextFormImage,
            FileType.XLS, excelExtractingService::extractTextFromXls,
            FileType.XLSX, excelExtractingService::extractTextFromXlsx
        );
    }

    @Override
    public Object processRequest(Map<String, String> request) {
        var pages = actionResolver.get(FileType.valueOf(prepareParameter(request, FILE_TYPE).toUpperCase()))
                .apply(prepareParameter(request, BUCKET_NAME), prepareParameter(request, FILE_KEY));
        log.info(TEXT_EXTRACT_LAMBDA_SUCCESS);
        return pages;
    }

    private String prepareParameter(Map<String, String> request, String name) {
        return Optional.ofNullable(request.get(name)).orElseThrow(
                () -> new AbsentRequiredParameter(String.format(ABSENT_REQUIRED_PARAMETER, name)));
    }
}
