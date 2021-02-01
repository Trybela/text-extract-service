package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.exception.TxtReadingException;
import com.avenga.fil.lt.service.TxtExtractingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static com.avenga.fil.lt.constant.GeneralConstant.TXT_READING_ERROR;

@Service
@RequiredArgsConstructor
public class TxtExtractingServiceImpl implements TxtExtractingService {

    private final S3Client s3Client;

    @Override
    public List<String> extractTextFromTxt(String bucketName, String key) {
        try (final var s3Object = s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build());
             final var reader = new BufferedReader(new InputStreamReader(s3Object))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new TxtReadingException(String.format(TXT_READING_ERROR, e.getMessage()));
        }
    }
}
