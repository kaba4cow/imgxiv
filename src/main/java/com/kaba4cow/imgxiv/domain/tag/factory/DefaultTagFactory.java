package com.kaba4cow.imgxiv.domain.tag.factory;

import com.kaba4cow.imgxiv.common.annotation.Factory;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Factory
public class DefaultTagFactory implements TagFactory {

	private final CategoryRepository categoryRepository;

	private final TagRepository tagRepository;

	@Override
	public Tag createTag(TagCreateRequest request) {
		Tag tag = new Tag();
		tag.getNameAndDescription().setName(request.getName());
		tag.getNameAndDescription().setDescription(request.getDescription());
		tag.setCategory(categoryRepository.findByIdOrThrow(request.getCategoryId()));
		Tag saved = tagRepository.save(tag);
		log.info("Created new tag: {}", saved);
		return saved;
	}

}
