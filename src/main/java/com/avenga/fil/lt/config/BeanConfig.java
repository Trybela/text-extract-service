package com.avenga.fil.lt.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.textract.TextractClient;

@Configuration
public class BeanConfig {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder().build();
    }

    @Bean
    public TextractClient textractClient() {
        return TextractClient.builder().build();
    }

    @Bean
    public LambdaClient lambdaClient() {
        return LambdaClient.builder().build();
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder().build();
    }

    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder().build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }
}
