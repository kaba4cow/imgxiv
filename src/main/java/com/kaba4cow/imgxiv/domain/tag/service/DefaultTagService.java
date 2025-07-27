package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.category.service.CategoryService;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.dto.TagEditRequest;
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
	public TagDto getTag(Long tagId) {
		return tagMapper.mapToDto(tagRepository.findByIdOrThrow(tagId));
	}

	@Override
	public TagDto editTag(Long tagId, TagEditRequest request) {
		Tag tag = tagRepository.findByIdOrThrow(tagId);
		Optional.ofNullable(request.getCategoryId())//
				.map(categoryRepository::findByIdOrThrow)//
				.ifPresent(tag::setCategory);
		Optional.ofNullable(request.getName())//
				.ifPresent(tag::setName);
		Optional.ofNullable(request.getDescription())//
				.ifPresent(tag::setDescription);
		Tag saved = tagRepository.save(tag);
		log.info("Updated tag: {}", saved);
		return tagMapper.mapToDto(saved);
	}

	@Override
	public List<TagDto> getTagsByCategory(Long categoryId) {
		Category category = categoryRepository.findByIdOrThrow(categoryId);
		return tagRepository.findByCategory(category).stream()//
				.map(tagMapper::mapToDto)//
				.collect(Collectors.toList());
	}

	@Override
	public List<TagDto> getTagsByDefaultCategory() {
		return getTagsByCategory(categoryService.getDefaultCategory().getId());
	}

	@Override
	@Transactional
	public Set<Tag> getOrCreateTagsByNames(Collection<? extends String> names) {
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
		tag.setName(name);
		tag.setDescription("");
		tag.setCategory(categoryService.getDefaultCategory());
		Tag saved = tagRepository.save(tag);
		log.info("Created new tag: {}", saved);
		return saved;
	}

}
