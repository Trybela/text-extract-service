package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.exception.LambdaException;
import com.avenga.fil.lt.model.TextTranslationResponse;
import com.avenga.fil.lt.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.avenga.fil.lt.constant.GeneralConstant.ERROR_RESPONSE_FORMAT;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Override
    public TextTranslationResponse createErrorResponse(Exception exception) {
        return createResponse(String.format(ERROR_RESPONSE_FORMAT, exception.getMessage()), determineHttpStatus(exception));
    }

    private TextTranslationResponse createResponse(String text, HttpStatus status) {
        return new TextTranslationResponse(text, status.value());
    }

    private HttpStatus determineHttpStatus(Throwable throwable) {
        return throwable instanceof LambdaException
                ? ((LambdaException) throwable).getHttpStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
