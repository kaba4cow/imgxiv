package com.kaba4cow.imgxiv.image.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.image.storage.stored.StoredImage;
import com.kaba4cow.imgxiv.image.storage.stored.StoredImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DatabaseImageStorage implements ImageStorage {

	private final StoredImageRepository storedImageRepository;

	@Override
	public void saveImage(String storageKey, MultipartFile file) {
		try {
			StoredImage image = StoredImage.builder()//
					.storageKey(storageKey)//
					.data(file.getBytes())//
					.build();
			storedImageRepository.save(image);
			logSaved(log, storageKey);
		} catch (Exception exception) {
			rethrowFailedToSave(log, storageKey, file, exception);
		}
	}

	@Override
	public InputStream getImage(String storageKey) {
		StoredImage image = storedImageRepository.findByIdOrThrow(storageKey);
		return new ByteArrayInputStream(image.getData());
	}

	@Override
	public void deleteImage(String storageKey) {
		storedImageRepository.deleteById(storageKey);
		logDeleted(log, storageKey);
	}

	@Override
	public void clearStorage() {
		storedImageRepository.deleteAll();
		logCleared(log);
	}

}
