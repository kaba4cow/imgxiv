package com.kaba4cow.imgxiv.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

	private final String bucketName;

	private final String region;

	private final String accessKey;

	private final String secretKey;

}
