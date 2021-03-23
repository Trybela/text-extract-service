package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.ExcelSheet;
import com.avenga.fil.lt.model.RequestPayloadData;

import java.util.List;

public interface ExcelExtractingService {

    List<ExcelSheet> extractTextFromXls(RequestPayloadData data);

    List<ExcelSheet> extractTextFromXlsx(RequestPayloadData data);
}
