package com.kaba4cow.imgxiv.storage;

import java.io.InputStream;

public interface ImageStorageService {

	void uploadImage(String storageKey, String contentType, long contentLength, InputStream input);

	InputStream getImage(String storageKey);

}
