package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.RequestPayloadData;

import java.util.Map;

public interface RequestParserService {

    RequestPayloadData parseAndPreparePayload(Map<String, String> request);
}
