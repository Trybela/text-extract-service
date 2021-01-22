package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.Pages;

public interface ImagePdfExtractingService {

    Pages extractTextFormImage(String bucketName, String key);

    Pages extractTextFromPdf(String bucketName, String key);
}
