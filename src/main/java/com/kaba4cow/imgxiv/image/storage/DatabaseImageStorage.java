package com.kaba4cow.imgxiv.image.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.image.storage.stored.StoredImage;
import com.kaba4cow.imgxiv.image.storage.stored.StoredImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class DatabaseImageStorage implements ImageStorage {

	private final StoredImageRepository storedImageRepository;

	@Override
	@SneakyThrows
	public void saveImage(String storageKey, MultipartFile file) {
		StoredImage image = StoredImage.builder()//
				.storageKey(storageKey)//
				.data(file.getBytes())//
				.build();
		storedImageRepository.save(image);
	}

	@Override
	public InputStream getImage(String storageKey) {
		StoredImage image = storedImageRepository.findByIdOrThrow(storageKey);
		return new ByteArrayInputStream(image.getData());
	}

	@Override
	public void deleteImage(String storageKey) {
		storedImageRepository.deleteById(storageKey);
	}

	@Override
	public void clearStorage() {
		storedImageRepository.deleteAll();
	}

}
