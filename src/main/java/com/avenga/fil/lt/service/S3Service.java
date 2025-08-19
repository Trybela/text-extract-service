package com.avenga.fil.lt.service;

public interface S3Service {

    void save(String documentName, String content);

    void save(String documentName, byte[] content);
}
