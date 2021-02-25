package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.RequestPayloadData;

import java.util.List;

public interface ExcelExtractingService {

    List<List<String>> extractTextFromXls(RequestPayloadData data);

    List<List<String>> extractTextFromXlsx(RequestPayloadData data);
}
