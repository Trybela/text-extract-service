package com.avenga.fil.lt.handler;

import com.avenga.fil.lt.model.Pages;
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
public class TextExtractFunction implements Function<Map<String, String>, Pages> {

    private final TextExtractService textExtractService;

    @Override
    public Pages apply(Map<String, String> stringStringMap) {
        log.info(TEXT_EXTRACT_LAMBDA_INVOKED);
        return textExtractService.processRequest(stringStringMap);
    }
}
