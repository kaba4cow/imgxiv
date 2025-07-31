package com.kaba4cow.imgxiv.image.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.kaba4cow.imgxiv.image.storage.stored.StoredImageRepository;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;

@RequiredArgsConstructor
@Configuration
public class ImageStorageConfig {

	@Profile("prod")
	@Bean
	public ImageStorage prodImageStorage(S3Client s3Client, @Value("${aws.s3.bucket-name}") String bucketName) {
		return new S3ImageStorage(s3Client, bucketName);
	}

	@Profile("dev")
	@Bean
	public ImageStorage devImageStorage(StoredImageRepository storedImageRepository) {
		return new DatabaseImageStorage(storedImageRepository);
	}

}
