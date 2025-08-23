package com.senifit.was.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final S3Presigner s3Presigner;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Value("${app.s3.presign.expiry-seconds}")
    private long presignExpirySeconds;

    public String generatePresignedUrl(String objectKey) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofSeconds(presignExpirySeconds))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return s3Presigner.presignGetObject(getObjectPresignRequest).url().toString();
        } catch (Exception e) {
            log.error("Failed to generate presigned URL for key: {}", objectKey, e);
            return null;
        }
    }
}
