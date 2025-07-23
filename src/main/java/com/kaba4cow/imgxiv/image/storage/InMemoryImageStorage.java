package com.kaba4cow.imgxiv.image.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class InMemoryImageStorage implements ImageStorage {

	private final Map<String, byte[]> storage = new ConcurrentHashMap<>();

	@Override
	public void saveImage(String storageKey, MultipartFile file) {
		try {
			storage.put(storageKey, file.getBytes());
			logSaved(log, storageKey);
		} catch (Exception exception) {
			rethrowFailedToSave(log, storageKey, file, exception);
		}
	}

	@Override
	public InputStream getImage(String storageKey) {
		if (!storage.containsKey(storageKey))
			throw new NotFoundException("Image", storageKey);
		return new ByteArrayInputStream(storage.get(storageKey));
	}

	@Override
	public void deleteImage(String storageKey) {
		storage.remove(storageKey);
		logDeleted(log, storageKey);
	}

	@Override
	public void clearStorage() {
		storage.clear();
		logCleared(log);
	}

}
