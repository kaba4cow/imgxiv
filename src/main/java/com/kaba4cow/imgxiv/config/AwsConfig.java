package com.kaba4cow.imgxiv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kaba4cow.imgxiv.aws.AwsProperties;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@RequiredArgsConstructor
@Configuration
public class AwsConfig {

	private final AwsProperties awsProperties;

	@Bean
	public S3Client s3Client() {
		AwsCredentials credentials = AwsBasicCredentials.builder()//
				.accessKeyId(awsProperties.getAccessKey())//
				.secretAccessKey(awsProperties.getSecretKey())//
				.build();
		return S3Client.builder()//
				.region(Region.of(awsProperties.getRegion()))//
				.credentialsProvider(StaticCredentialsProvider.create(credentials))//
				.build();
	}

}
