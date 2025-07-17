package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;

public interface TagService {

	TagDto create(TagCreateRequest request);

	List<TagDto> findAll();

	List<TagDto> findByCategoryId(Long categoryId);

	Set<Tag> findByIdsOrThrow(Collection<? extends Long> ids);

	Tag findByIdOrThrow(Long id);

}
