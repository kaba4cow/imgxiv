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
public class S3Config {

	@Bean
	public S3Client s3Client(//
			@Value("${aws.s3.region}") String region, //
			@Value("${aws.s3.access-key}") String accessKey, //
			@Value("${aws.s3.secret-key}") String secretKey) {
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
