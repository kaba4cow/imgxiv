package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.List;

import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;

public interface TagService {

	TagDto create(TagCreateRequest request);

	List<TagDto> findByCategoryId(Long categoryId);

}
