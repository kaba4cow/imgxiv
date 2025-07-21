package com.kaba4cow.imgxiv.image.storage;

import java.io.InputStream;

public interface ImageStorage {

	void uploadImage(String storageKey, String contentType, long contentLength, InputStream input);

	InputStream getImage(String storageKey);

	void deleteImage(String storageKey);

}
