package com.kaba4cow.imgxiv.image.storage;

import java.io.InputStream;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.common.exception.ImageUploadException;

public interface ImageStorage {

	void saveImage(String storageKey, MultipartFile file);

	InputStream getImage(String storageKey);

	void deleteImage(String storageKey);

	void clearStorage();

	default void logSaved(Logger log, String storageKey) {
		log.info("Saved image: {}", storageKey);
	}

	default void rethrowFailedToSave(Logger log, String storageKey, MultipartFile file, Exception exception) {
		log.error("Failed to save image: {} (contentType={}, contentLength={}). Cause: {}", //
				storageKey, //
				file.getContentType(), //
				file.getSize(), //
				exception.getMessage(), //
				exception//
		);
		throw new ImageUploadException("Failed to save image", exception);
	}

	default void logDeleted(Logger log, String storageKey) {
		log.info("Deleted image: {}", storageKey);
	}

	default void logCleared(Logger log) {
		log.info("Cleared storage");
	}

}
