package com.avenga.fil.lt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TextTranslationResponse {

    private String text;
    private Integer httpStatus;
}
