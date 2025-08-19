package com.avenga.fil.lt.exception;

import org.springframework.http.HttpStatus;

public class TextTranslateInvokingException extends LambdaException {

    public TextTranslateInvokingException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
