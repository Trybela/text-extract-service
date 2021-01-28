package com.avenga.fil.lt.constant;

public final class GeneralConstant {

    //Messages
    public static final String ABSENT_REQUIRED_PARAMETER = "Error. Absent required parameter %s";
    public static final String TEXT_EXTRACT_PDF_ERROR = "Error during extracting text from pdf process.";
    public static final String LOADING_PDF_ERROR = "Error during loading pdf process.";
    public static final String XLS_READING_ERROR = "Error during reading xls document - %s";
    public static final String XLSX_READING_ERROR = "Error during reading xlsx document - %s";

    //Logging
    public static final String TEXT_EXTRACT_LAMBDA_INVOKED = "Text extract lambda invoked.";
    public static final String TEXT_EXTRACT_LAMBDA_SUCCESS = "Lambda successfully extracted text from document";
    public static final String EXTRACTING_FROM_IMAGE = "Start to extract text from image.";
    public static final String EXTRACTING_FROM_PDF = "Start to extract text from pdf document.";

    private GeneralConstant() {
        throw new UnsupportedOperationException();
    }
}
