package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.model.FileType;
import com.avenga.fil.lt.model.RequestPayloadData;
import com.avenga.fil.lt.model.TextTranslateInput;
import com.avenga.fil.lt.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private final SnsService snsService;

    public TextExtractServiceImpl(ImagePdfExtractingService imagePdfExtractingService,
                                  ExcelExtractingService excelExtractingService,
                                  TxtExtractingService txtExtractingService, TextTranslateService textTranslateService,
                                  RequestParserService requestParserService, ObjectMapper objectMapper,
                                  ResponseService responseService, SnsService snsService) {
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
        this.snsService = snsService;
    }

    @Override
    public Object processRequest(Map<String, String> request) {
        RequestPayloadData data = null;
        try {
            data = requestParserService.parseAndPreparePayload(request);
            var pages = actionResolver.get(FileType.valueOf(data.getFileType().toUpperCase()))
                    .apply(data);
            invokingTranslateTextProcess(pages, data);
            log.info(TEXT_EXTRACT_LAMBDA_SUCCESS);
            return pages;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            snsService.sendEmail(formatEmailMessage(exception, data));
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
                .unit(data.getUnit())
                .xlsColumns(data.getXlsColumns())
                .text(objectMapper.writeValueAsString(content))
                .build());
        log.info(TEXT_TRANSLATION_LAMBDA_INVOKED);
    }

    private String formatEmailMessage(Exception exception, RequestPayloadData data) {
        var message = "Hi Team, \n" +
                "Problem occurred while translation the document using Language Translation service. Relevant details are provided as below -\n\n" +
                "Document: '%s'\n" +
                "Source Language: %s,\nTarget Language: %s\n" +
                "Client Id: '%s'\n" +
                "Business unit: '%s'\n" +
                "Date: %s\n" +
                "Exception: %s.\n\n" +
                "Kindly investigate the issue.\n\n" +
                "Regards\nLanguage Translation Support";
        var blank = "";
        var dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var date = LocalDateTime.now().format(dateTimeFormatter);
        if (Objects.isNull(data)) {
            return String.format(message, blank, blank, blank, blank, blank, date, exception.getMessage());
        }
        var document = Optional.ofNullable(data.getFileKey()).orElse(blank);
        var userId = Optional.ofNullable(data.getUserId()).orElse(blank);
        var businessUnit = Optional.ofNullable(data.getUnit()).orElse(blank);
        var sourceLanguage = Optional.ofNullable(data.getSourceLanguage()).orElse(blank);
        var targetLanguage = Optional.ofNullable(data.getTargetLanguage()).orElse(blank);
        return String.format(message, document, sourceLanguage, targetLanguage, userId, businessUnit, date, exception.getMessage());
    }

}
