package com.avenga.fil.lt.exception;

import org.springframework.http.HttpStatus;

public class WordReadingException extends LambdaException {

    public WordReadingException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
