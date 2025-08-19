package com.avenga.fil.lt.handler;

import com.avenga.fil.lt.model.Pages;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import java.util.Map;

public class TextExtractLambdaHandler extends SpringBootRequestHandler<Map<String, String>, Pages> {}
