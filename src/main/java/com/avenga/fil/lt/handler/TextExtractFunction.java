package com.avenga.fil.lt.handler;

import com.avenga.fil.lt.service.TextExtractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

import static com.avenga.fil.lt.constant.GeneralConstant.TEXT_EXTRACT_LAMBDA_INVOKED;

@Slf4j
@Component
@RequiredArgsConstructor
public class TextExtractFunction implements Function<Map<String, String>, Object> {

    private final TextExtractService textExtractService;

    @Override
    public Object apply(Map<String, String> request) {
        log.info(TEXT_EXTRACT_LAMBDA_INVOKED);
        return textExtractService.processRequest(request);
    }
}
