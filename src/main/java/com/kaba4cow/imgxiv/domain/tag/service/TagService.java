package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;

public interface TagService {

	TagDto getTag(Long tagId);

	List<TagDto> getTagsByCategory(Long categoryId);

	List<TagDto> getTagsByDefaultCategory();

	Set<Tag> getOrCreateTagsByNames(Collection<? extends String> names);

}
