package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.exception.AbsentRequiredParameter;
import com.avenga.fil.lt.model.RequestPayloadData;
import com.avenga.fil.lt.service.RequestParserService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.avenga.fil.lt.constant.ApiEventConstants.*;
import static com.avenga.fil.lt.constant.GeneralConstant.ABSENT_REQUIRED_PARAMETER;

@Service
public class RequestParserServiceImpl implements RequestParserService {

    @Override
    public RequestPayloadData parseAndPreparePayload(Map<String, String> request) {
        return RequestPayloadData.builder()
                .bucketName(prepareParameter(request, BUCKET_NAME))
                .fileKey(prepareParameter(request, FILE_KEY))
                .sourceLanguage(prepareParameter(request, FROM_LANGUAGE))
                .targetLanguage(prepareParameter(request, TO_LANGUAGE))
                .userId(prepareParameter(request, USER_ID))
                .documentName(prepareParameter(request, DOCUMENT_NAME))
                .fileType(prepareParameter(request, FILE_TYPE))
                .build();
    }

    private String prepareParameter(Map<String, String> request, String name) {
        return Optional.ofNullable(request.get(name)).orElseThrow(
                () -> new AbsentRequiredParameter(String.format(ABSENT_REQUIRED_PARAMETER, name)));
    }
}
