package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.RequestPayloadData;

public interface WordExtractingService {

    byte[] extractText(RequestPayloadData data);
}
