package com.kaba4cow.imgxiv.image.service;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.domain.embeddable.PostImage;
import com.kaba4cow.imgxiv.image.ImageResource;

public interface ImageService {

	PostImage createImage(MultipartFile file);

	ImageResource getImage(PostImage postImage);

	void deleteImage(PostImage postImage);

}
