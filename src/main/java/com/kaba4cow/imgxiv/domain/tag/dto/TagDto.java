package com.kaba4cow.imgxiv.domain.tag.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TagDto {

	private final Long id;

	private final String name;

	private final String description;

	private final Long categoryId;

}
