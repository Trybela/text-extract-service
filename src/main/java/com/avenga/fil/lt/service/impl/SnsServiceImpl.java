package com.avenga.fil.lt.service.impl;

import com.avenga.fil.lt.service.SnsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class SnsServiceImpl implements SnsService {

    private final SnsClient snsClient;
    private final String topicArn;

    public SnsServiceImpl(@Value("${snsTopicArn}") String topicArn, SnsClient snsClient) {
        this.snsClient = snsClient;
        this.topicArn = topicArn;
    }

    @Override
    public void sendEmail(String message) {
        PublishRequest request = PublishRequest.builder()
                .subject("Text Extract service - Exception")
                .message(message)
                .topicArn(topicArn)
                .build();
        snsClient.publish(request);
    }
}
