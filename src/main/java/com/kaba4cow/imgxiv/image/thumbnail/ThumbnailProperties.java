package com.kaba4cow.imgxiv.image.thumbnail;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "thumbnail")
public class ThumbnailProperties {

	private final int imageWidth;

	private final int imageHeight;

	private final String imageFormat;

	private final String contentType;

}
