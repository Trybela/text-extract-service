package com.avenga.fil.lt.service;

import java.util.List;

public interface ExcelExtractingService {

    List<List<String>> extractTextFromXls(String bucketName, String key);

    List<List<String>> extractTextFromXlsx(String bucketName, String key);
}
