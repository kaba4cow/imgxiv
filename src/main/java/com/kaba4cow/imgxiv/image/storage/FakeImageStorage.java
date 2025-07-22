package com.kaba4cow.imgxiv.image.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.common.exception.ImageUploadException;
import com.kaba4cow.imgxiv.common.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FakeImageStorage implements ImageStorage {

	private final Map<String, byte[]> storage = new ConcurrentHashMap<>();

	@Override
	public void uploadImage(String storageKey, MultipartFile file) {
		try {
			byte[] bytes = file.getBytes();
			storage.put(storageKey, bytes);
			log.debug("Uploaded image: {}", storageKey);
		} catch (Exception exception) {
			throw new ImageUploadException(String.format("Could not upload image: %s", storageKey), exception);
		}
	}

	@Override
	public InputStream getImage(String storageKey) {
		if (!storage.containsKey(storageKey))
			throw new NotFoundException(String.format("Image not found: %s", storageKey));
		return new ByteArrayInputStream(storage.get(storageKey));
	}

	@Override
	public void deleteImage(String storageKey) {
		storage.remove(storageKey);
		log.debug("Deleted image: {}", storageKey);
	}

	@Override
	public void clearStorage() {}

}
