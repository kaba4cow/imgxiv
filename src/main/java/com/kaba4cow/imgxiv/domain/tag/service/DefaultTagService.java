package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.exception.NameConflictException;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.dto.TagMapper;
import com.kaba4cow.imgxiv.domain.tag.factory.TagFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultTagService implements TagService {

	private final TagRepository tagRepository;

	private final CategoryRepository categoryRepository;

	private final TagFactory tagFactory;

	private final TagMapper tagMapper;

	@Override
	public TagDto create(TagCreateRequest request) {
		if (tagRepository.existsByName(request.getName()))
			throw new NameConflictException("Tag with this name already exists");
		return tagMapper.mapToDto(tagFactory.createTag(request));
	}

	@Override
	public List<TagDto> findAll() {
		return tagRepository.findAll().stream()//
				.map(tagMapper::mapToDto)//
				.collect(Collectors.toList());
	}

	@Override
	public List<TagDto> findByCategoryId(Long categoryId) {
		Category category = categoryRepository.findByIdOrThrow(categoryId);
		return tagRepository.findByCategory(category).stream()//
				.map(tagMapper::mapToDto)//
				.collect(Collectors.toList());
	}

}
