package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.TextTranslationResponse;

public interface ResponseService {

    TextTranslationResponse createErrorResponse(Exception exception);
}
