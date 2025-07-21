package com.kaba4cow.imgxiv.storage;

import java.io.InputStream;

public interface ImageStorageService {

	void uploadImage(String storageKey, InputStream input, long contentLength, String contentType);

}
