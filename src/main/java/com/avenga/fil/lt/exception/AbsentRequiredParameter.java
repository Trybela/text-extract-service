package com.avenga.fil.lt.exception;

import org.springframework.http.HttpStatus;

public class AbsentRequiredParameter extends LambdaException {

    public AbsentRequiredParameter(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
