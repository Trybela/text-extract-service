package com.avenga.fil.lt.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class TextExtractFunction implements Function<Map<String, String>, String> {

    @Override
    public String apply(Map<String, String> stringStringMap) {
        return "Text extract lambda!";
    }
}
