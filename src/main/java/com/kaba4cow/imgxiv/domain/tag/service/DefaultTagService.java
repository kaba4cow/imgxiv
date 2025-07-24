package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.common.exception.NameConflictException;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.category.service.CategoryService;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.dto.TagMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultTagService implements TagService {

	private final TagRepository tagRepository;

	private final CategoryRepository categoryRepository;

	private final CategoryService categoryService;

	private final TagMapper tagMapper;

	@Override
	public TagDto create(TagCreateRequest request) {
		if (tagRepository.existsByName(request.getName()))
			throw new NameConflictException("Tag with this name already exists");
		Tag tag = new Tag();
		tag.getNameAndDescription().setName(request.getName());
		tag.getNameAndDescription().setDescription(request.getDescription());
		tag.setCategory(categoryRepository.findByIdOrThrow(request.getCategoryId()));
		Tag saved = tagRepository.save(tag);
		log.info("Created new tag: {}", saved);
		return tagMapper.mapToDto(saved);
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

	@Override
	@Transactional
	public Set<Tag> getOrCreateTagsByNames(Set<String> names) {
		return names.stream()//
				.map(this::normalizeTagName)//
				.map(this::getOrCreateTagByName)//
				.collect(Collectors.toSet());
	}

	private String normalizeTagName(String name) {
		return name.trim().toLowerCase();
	}

	private Tag getOrCreateTagByName(String name) {
		return tagRepository.findByName(name).orElseGet(() -> createNewTag(name));
	}

	private Tag createNewTag(String name) {
		Tag tag = new Tag();
		tag.getNameAndDescription().setName(name);
		tag.getNameAndDescription().setDescription("");
		tag.setCategory(categoryService.getDefaultCategory());
		Tag saved = tagRepository.save(tag);
		log.info("Created new tag: {}", saved);
		return saved;
	}

}
