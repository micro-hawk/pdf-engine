package com.example.demo.service.api;

import java.io.IOException;
import org.springframework.http.ResponseEntity;

public interface S3Service {

    ResponseEntity<byte[]> getByteDataFromS3(String key) throws IOException;
}
