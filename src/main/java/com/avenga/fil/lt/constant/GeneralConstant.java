package com.avenga.fil.lt.constant;

public final class GeneralConstant {

    //Messages
    public static final String ABSENT_REQUIRED_PARAMETER = "Error. Absent required parameter %s";
    public static final String TEXT_EXTRACT_PDF_ERROR = "Error during extracting text from pdf process.";
    public static final String LOADING_PDF_ERROR = "Error during loading pdf process.";
    public static final String XLS_READING_ERROR = "Error during reading xls document - %s";
    public static final String XLSX_READING_ERROR = "Error during reading xlsx document - %s";
    public static final String TXT_READING_ERROR = "Error during reading txt document - %s";
    public static final String TEXT_TRANSLATE_INVOKING_ERROR = "Error during invoking translate lambda - %s.";

    //Logging
    public static final String TEXT_EXTRACT_LAMBDA_INVOKED = "Text extract lambda invoked.";
    public static final String TEXT_EXTRACT_LAMBDA_SUCCESS = "Lambda successfully extracted text from document";
    public static final String EXTRACTING_FROM_IMAGE = "Start to extract text from image.";
    public static final String EXTRACTING_FROM_PDF = "Start to extract text from pdf document.";
    public static final String TEXT_TRANSLATION_LAMBDA_INVOKED = "The text translation process has invoked.";
    public static final String EXTRACTED_TEXT_SUCCESSFULLY_SAVED = "The extracted text successfully saved to s3.";

    //Response
    public static final String ERROR_RESPONSE_FORMAT = "ErrorMessage: %s";

    private GeneralConstant() {
        throw new UnsupportedOperationException();
    }
}
