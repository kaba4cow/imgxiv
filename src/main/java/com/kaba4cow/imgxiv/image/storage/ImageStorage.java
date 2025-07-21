package com.kaba4cow.imgxiv.image.storage;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorage {

	void uploadImage(String storageKey, MultipartFile file);

	InputStream getImage(String storageKey);

	void deleteImage(String storageKey);

	void clearStorage();

}
