package com.kaba4cow.imgxiv.image.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.domain.embeddable.PostImage;

public interface ImageService {

	void uploadImage(MultipartFile file);

	InputStream getImage(PostImage postImage);

	void deleteImage(PostImage postImage);

}
