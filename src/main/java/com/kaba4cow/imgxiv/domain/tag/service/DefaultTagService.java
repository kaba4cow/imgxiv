package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.Collection;
import java.util.List;
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
	public TagDto getTagById(Long tagId) {
		return tagMapper.mapToDto(tagRepository.findByIdOrThrow(tagId));
	}

	@Override
	public List<TagDto> getTagsByCategoryId(Long categoryId) {
		Category category = categoryRepository.findByIdOrThrow(categoryId);
		return tagRepository.findByCategory(category).stream()//
				.map(tagMapper::mapToDto)//
				.collect(Collectors.toList());
	}

	@Override
	public List<TagDto> getTagsByDefaultCategory() {
		return getTagsByCategoryId(categoryService.getDefaultCategory().getId());
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
		tag.getNameAndDescription().setName(name);
		tag.getNameAndDescription().setDescription("");
		tag.setCategory(categoryService.getDefaultCategory());
		Tag saved = tagRepository.save(tag);
		log.info("Created new tag: {}", saved);
		return saved;
	}

}
