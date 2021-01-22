package com.avenga.fil.lt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {

    PDF("PDF"), JPG("JPG"), JPEG("JPEG"), PNG("PNG"), BMP("BMP");

    private String fileType;
}
