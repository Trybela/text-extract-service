package com.avenga.fil.lt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class TextTranslateInput {

    private String fileKey;
    private String sourceLanguage;
    private String targetLanguage;
    private String userId;
    private String documentName;
    private String fileType;
    private String unit;
    private String xlsColumns;
    private Object text;
}
