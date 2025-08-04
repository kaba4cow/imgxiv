package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.dto.TagEditRequest;
import com.kaba4cow.imgxiv.domain.tag.model.Tag;

public interface TagService {

	TagDto getTag(Long tagId);

	TagDto editTag(Long tagId, TagEditRequest request);

	List<TagDto> getTagsByCategory(Long categoryId);

	List<TagDto> getTagsByDefaultCategory();

	Set<Tag> getOrCreateTagsByNames(Collection<? extends String> names);

}
