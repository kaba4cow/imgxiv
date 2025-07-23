package com.kaba4cow.imgxiv.image.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class InMemoryImageStorage implements ImageStorage {

	private final Map<String, byte[]> storage = new ConcurrentHashMap<>();

	@Override
	@SneakyThrows
	public void saveImage(String storageKey, MultipartFile file) {
		storage.put(storageKey, file.getBytes());
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
	}

	@Override
	public void clearStorage() {
		storage.clear();
	}

}
