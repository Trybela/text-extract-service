package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.Pages;
import com.avenga.fil.lt.model.RequestPayloadData;

public interface ImagePdfExtractingService {

    Pages extractTextFormImage(RequestPayloadData data);

    Pages extractTextFromPdf(RequestPayloadData data);
}
