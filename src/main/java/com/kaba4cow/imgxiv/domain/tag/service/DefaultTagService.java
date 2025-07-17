package com.kaba4cow.imgxiv.domain.tag.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.exception.NameConflictException;
import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.dto.TagMapper;
import com.kaba4cow.imgxiv.util.PersistLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultTagService implements TagService {

	private final TagRepository tagRepository;

	private final CategoryRepository categoryRepository;

	private final TagMapper tagMapper;

	@Override
	public TagDto create(TagCreateRequest request) {
		if (tagRepository.existsByName(request.getName()))
			throw new NameConflictException("Tag with this name already exists");
		Tag tag = createTag(request);
		return tagMapper.mapToDto(tag);
	}

	private Tag createTag(TagCreateRequest request) {
		Category category = categoryRepository.findById(request.getCategoryId())//
				.orElseThrow(() -> new NotFoundException("Category not found"));
		Tag tag = new Tag();
		tag.getNameAndDescription().setName(request.getName());
		tag.getNameAndDescription().setDescription(request.getDescription());
		tag.setCategory(category);
		return PersistLog.log(tagRepository.save(tag));
	}

	@Override
	public List<TagDto> findByCategoryId(Long categoryId) {
		return tagRepository.findByCategoryId(categoryId).stream()//
				.map(tagMapper::mapToDto)//
				.collect(Collectors.toList());
	}

	@Override
	public Set<Tag> findByIdsOrThrow(Collection<? extends Long> ids) {
		return ids.stream()//
				.distinct()//
				.map(this::findByIdOrThrow)//
				.collect(Collectors.toSet());
	}

	@Override
	public Tag findByIdOrThrow(Long id) {
		return tagRepository.findById(id)//
				.orElseThrow(() -> new NotFoundException(String.format("Tag not found: %s", id)));
	}

}
