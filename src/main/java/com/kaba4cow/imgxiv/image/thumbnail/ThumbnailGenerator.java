package com.kaba4cow.imgxiv.image.thumbnail;

import org.springframework.web.multipart.MultipartFile;

public interface ThumbnailGenerator {

	MultipartFile generateThumbnail(MultipartFile file);

}
