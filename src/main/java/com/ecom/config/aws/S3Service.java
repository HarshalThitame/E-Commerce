package com.ecom.config.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3Service {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public S3Service(AmazonS3 s3Client, @Value("${aws.s3.bucket.name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public String uploadFile(String keyName, InputStream fileStream) {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, fileStream, null);
            PutObjectResult result = s3Client.putObject(putObjectRequest);

            return s3Client.getUrl(bucketName, keyName).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
}
