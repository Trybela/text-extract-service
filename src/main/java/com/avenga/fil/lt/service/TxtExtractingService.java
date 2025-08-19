package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.RequestPayloadData;

import java.util.List;

public interface TxtExtractingService {

    List<String> extractTextFromTxt(RequestPayloadData data);
}
