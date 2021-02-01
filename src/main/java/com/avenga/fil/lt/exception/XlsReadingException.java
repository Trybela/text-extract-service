package com.avenga.fil.lt.exception;

import org.springframework.http.HttpStatus;

public class XlsReadingException extends LambdaException {

    public XlsReadingException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
