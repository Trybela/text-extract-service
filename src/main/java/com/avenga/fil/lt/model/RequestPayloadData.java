package com.avenga.fil.lt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestPayloadData {

    private String bucketName;
    private String fileKey;
    private String sourceLanguage;
    private String targetLanguage;
    private String userId;
    private String documentName;
    private String fileType;
    private String unit;
}
