package com.kaba4cow.imgxiv.image.storage;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.common.exception.ImageUploadException;
import com.kaba4cow.imgxiv.common.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsImageStorage implements ImageStorage {

	private final S3Client s3Client;

	@Value("${aws.bucket-name}")
	private String bucketName;

	@Override
	public void uploadImage(String storageKey, MultipartFile file) {
		String contentType = file.getContentType();
		long contentLength = file.getSize();
		try (InputStream input = file.getInputStream()) {
			s3Client.putObject(//
					buildPutRequest(storageKey, contentLength, contentType), //
					buildPutRequestBody(input, contentLength)//
			);
			log.info("Uploaded image: storageKey={}, contentType={}, contentLength={}", //
					storageKey, //
					contentType, //
					contentLength//
			);
		} catch (Exception exception) {
			log.info("Failed to upload image: storageKey={}, contentType={}, contentLength={}. Cause: {}", //
					storageKey, //
					contentType, //
					contentLength, //
					exception.getMessage(), //
					exception//
			);
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
			throw new NotFoundException("Image", storageKey);
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
		log.info("Deleted image {}", storageKey);
	}

	private DeleteObjectRequest buildDeleteObjectRequest(String storageKey) {
		return DeleteObjectRequest.builder()//
				.bucket(bucketName)//
				.key(storageKey)//
				.build();
	}

	@Override
	public void clearStorage() {
		log.info("Clearing storage...");
		ListObjectsV2Request listRequest = buildListObjectsRequest();
		ListObjectsV2Response listResponse;
		do {
			listResponse = s3Client.listObjectsV2(listRequest);
			List<ObjectIdentifier> objectsToDelete = getObjectsToDelete(listResponse);
			if (!objectsToDelete.isEmpty())
				s3Client.deleteObjects(buildDeleteObjectsRequests(objectsToDelete));
			listRequest = listRequest.toBuilder()//
					.continuationToken(listResponse.nextContinuationToken())//
					.build();
		} while (listResponse.isTruncated());
		log.info("Storage cleared");
	}

	private ListObjectsV2Request buildListObjectsRequest() {
		return ListObjectsV2Request.builder()//
				.bucket(bucketName)//
				.build();
	}

	private List<ObjectIdentifier> getObjectsToDelete(ListObjectsV2Response listObjectsResponse) {
		return listObjectsResponse.contents().stream()//
				.map(this::buildObject)//
				.toList();
	}

	private ObjectIdentifier buildObject(S3Object s3Object) {
		return ObjectIdentifier.builder()//
				.key(s3Object.key())//
				.build();
	}

	private DeleteObjectsRequest buildDeleteObjectsRequests(List<ObjectIdentifier> objectsToDelete) {
		return DeleteObjectsRequest.builder().bucket(bucketName)//
				.delete(Delete.builder()//
						.objects(objectsToDelete)//
						.build()//
				).build();
	}

}
