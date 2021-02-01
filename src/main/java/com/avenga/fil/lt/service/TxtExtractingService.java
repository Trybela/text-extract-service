package com.avenga.fil.lt.service;

import java.util.List;

public interface TxtExtractingService {

    List<String> extractTextFromTxt(String bucketName, String key);
}
