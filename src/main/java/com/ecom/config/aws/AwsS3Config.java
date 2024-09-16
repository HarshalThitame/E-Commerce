package com.ecom.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

//    @Value("${aws.access.key.id}")
//    private String awsAccessKeyId;
//
//    @Value("${aws.secret.access.key}")
//    private String awsSecretAccessKey;
//
//    @Value("${aws.region}")
//    private String awsRegion;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAYRH5ND6JDPJFGWEN", "RWySO+RJaU9LTjBU0NYs8lMhcfrZvbWpxjeO9UBl");
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_NORTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
