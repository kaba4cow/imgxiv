package com.kaba4cow.imgxiv.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.exception.ImageUploadException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
@Service
public class AwsImageStorageService implements ImageStorageService {

	private final S3Client s3Client;

	@Value("${aws.bucket-name}")
	private String bucketName;

	@Override
	public void uploadImage(String key, InputStream input, long contentLength, String contentType) {
		try {
			s3Client.putObject(//
					PutObjectRequest.builder()//
							.bucket(bucketName)//
							.key(key)//
							.contentLength(contentLength)//
							.contentType(contentType)//
							.build(), //
					RequestBody.fromInputStream(input, contentLength)//
			);
		} catch (Exception exception) {
			throw new ImageUploadException("Failed to upload image to S3", exception);
		}
	}

}
