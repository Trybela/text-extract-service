package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.Pages;

import java.util.Map;

public interface TextExtractService {

    Pages processRequest(Map<String, String> request);
}
