package com.avenga.fil.lt.handler;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import java.util.Map;

public class TextExtractLambdaHandler extends SpringBootRequestHandler<Map<String, String>, String> {}
