package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.List;
import java.util.Set;

import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;

public interface TagService {

	TagDto getTagById(Long tagId);

	List<TagDto> getTagsByCategoryId(Long categoryId);

	List<TagDto> getTagsByDefaultCategory();

	Set<Tag> getOrCreateTagsByNames(Set<String> names);

}
