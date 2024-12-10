package com.bit.microservices.service_approval.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.ssm.SsmClient;


@Configuration
@Slf4j
public class AWSConfig {
    @Value("${cloud.aws.credentials.access-key:0}")
    private String access;

    @Value("${cloud.aws.credentials.secret-key:0}")
    private String secret;
 @Bean
 @Primary
 public AwsCredentialsProvider buildAwsCredentialsProvider(){
    if (access.equalsIgnoreCase("0") || secret.equalsIgnoreCase("0")) {
       log.warn("Couldn't get AccessKey & SecretKey AWS!");
       AwsCredentials awsCredentials = AwsBasicCredentials.create(access, secret);
       return StaticCredentialsProvider.create(awsCredentials);
    } else {
       AwsCredentials awsCredentials = AwsBasicCredentials.create(access, secret);
       return StaticCredentialsProvider.create(awsCredentials);
    }
 }

    @Bean
    public KinesisAsyncClient buildKinesisAsyncClient() {
        return KinesisAsyncClient
            .builder()
            .credentialsProvider(buildAwsCredentialsProvider())
            .region(Region.AP_SOUTHEAST_1)
            .build();
    }

     @Bean
     public SsmClient ssmClient() {
       return SsmClient
           .builder()
           .credentialsProvider(buildAwsCredentialsProvider())
           .httpClient(UrlConnectionHttpClient.builder().build())
           .region(Region.AP_SOUTHEAST_1)
           .build();
     }

    @Bean
    public SqsAsyncClient sqsClient() {
        return SqsAsyncClient
                .builder()
                .credentialsProvider(buildAwsCredentialsProvider())
                .region(Region.AP_SOUTHEAST_1)
                .build();
    }
}
