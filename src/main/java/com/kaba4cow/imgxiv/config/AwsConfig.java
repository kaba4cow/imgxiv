package com.kaba4cow.imgxiv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@RequiredArgsConstructor
@Configuration
public class AwsConfig {

	@Bean
	public S3Client s3Client(//
			@Value("${aws.region}") String region, //
			@Value("${aws.access-key}") String accessKey, //
			@Value("${aws.secret-key}") String secretKey) {
		AwsCredentials credentials = AwsBasicCredentials.builder()//
				.accessKeyId(accessKey)//
				.secretAccessKey(secretKey)//
				.build();
		return S3Client.builder()//
				.region(Region.of(region))//
				.credentialsProvider(StaticCredentialsProvider.create(credentials))//
				.build();
	}

}
