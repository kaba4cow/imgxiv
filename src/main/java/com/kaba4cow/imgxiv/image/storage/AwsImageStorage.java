package com.kaba4cow.imgxiv.image.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.common.exception.ImageUploadException;
import com.kaba4cow.imgxiv.common.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsImageStorage implements ImageStorage {

	private final S3Client s3Client;

	@Value("${aws.bucket-name}")
	private String bucketName;

	@Override
	public void uploadImage(String storageKey, String contentType, long contentLength, InputStream input) {
		try {
			s3Client.putObject(//
					buildPutRequest(storageKey, contentLength, contentType), //
					buildPutRequestBody(input, contentLength)//
			);
			log.info("Image upload successfull: storageKey={} contentType={} contentLength={}", storageKey, contentType,
					contentLength);
		} catch (Exception exception) {
			log.info("Image upload failed: storageKey={} contentType={} contentLength={}. Cause: {}", storageKey, contentType,
					contentLength, exception.getMessage());
			throw new ImageUploadException("Failed to upload image to S3", exception);
		}
	}

	private PutObjectRequest buildPutRequest(String storageKey, long contentLength, String contentType) {
		return PutObjectRequest.builder()//
				.bucket(bucketName)//
				.key(storageKey)//
				.contentLength(contentLength)//
				.contentType(contentType)//
				.build();
	}

	private RequestBody buildPutRequestBody(InputStream input, long contentLength) {
		return RequestBody.fromInputStream(input, contentLength);
	}

	@Override
	public InputStream getImage(String storageKey) {
		try {
			return s3Client.getObject(buildGetObjectRequest(storageKey));
		} catch (Exception exception) {
			throw new NotFoundException(String.format("Image not found: %s", storageKey));
		}
	}

	private GetObjectRequest buildGetObjectRequest(String storageKey) {
		return GetObjectRequest.builder()//
				.bucket(bucketName)//
				.key(storageKey)//
				.build();
	}

	@Override
	public void deleteImage(String storageKey) {
		s3Client.deleteObject(buildDeleteObjectRequest(storageKey));
	}

	private DeleteObjectRequest buildDeleteObjectRequest(String storageKey) {
		return DeleteObjectRequest.builder()//
				.bucket(bucketName)//
				.key(storageKey)//
				.build();
	}

}
