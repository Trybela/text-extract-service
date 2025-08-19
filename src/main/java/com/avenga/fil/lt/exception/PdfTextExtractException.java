package com.avenga.fil.lt.exception;

import org.springframework.http.HttpStatus;

public class PdfTextExtractException extends LambdaException {

    public PdfTextExtractException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
