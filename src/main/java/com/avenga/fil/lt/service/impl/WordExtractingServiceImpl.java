package com.avenga.fil.lt.service.impl;

import com.amazonaws.util.IOUtils;
import com.avenga.fil.lt.exception.WordReadingException;
import com.avenga.fil.lt.model.RequestPayloadData;
import com.avenga.fil.lt.service.WordExtractingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;

import static com.avenga.fil.lt.constant.GeneralConstant.WORD_READING_ERROR;

@Service
@RequiredArgsConstructor
public class WordExtractingServiceImpl implements WordExtractingService {

    private final S3Client s3Client;

    @Override
    public byte[] extractText(RequestPayloadData data) {
        try (var doc = documentStream(data.getBucketName(), data.getFileKey())) {
            return IOUtils.toByteArray(doc);
        } catch (IOException e) {
            throw new WordReadingException(String.format(WORD_READING_ERROR, e.getMessage()));
        }
    }

    private InputStream documentStream(String bucketName, String key) {
        return s3Client.getObjectAsBytes(GetObjectRequest.builder().bucket(bucketName).key(key).build()).asInputStream();
    }
}
