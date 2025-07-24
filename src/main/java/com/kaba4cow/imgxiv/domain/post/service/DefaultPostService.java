package com.kaba4cow.imgxiv.domain.post.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.dto.PaginationParams;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostMapper;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.security.PostSecurity;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.service.TagService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.image.ImageResource;
import com.kaba4cow.imgxiv.image.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {

	private final PostQueryExecutorService postQueryExecutorService;

	private final PostRepository postRepository;

	private final TagService tagService;

	private final ImageService imageService;

	private final PostSecurity postSecurity;

	private final PostMapper postMapper;

	@Override
	public PostDto createPost(PostCreateRequest request, User author) {
		Set<Tag> tags = tagService.getOrCreateTagsByNames(request.getTagNames());
		Post post = new Post();
		tags.forEach(post::addTag);
		post.setAuthor(author);
		post.setPostImage(imageService.createImages(request.getImage()));
		Post saved = postRepository.save(post);
		log.info("Created new post: {}", saved);
		return postMapper.mapToDto(saved);
	}

	@Override
	public PostDto getPost(Long id) {
		return postMapper.mapToDto(postRepository.findByIdOrThrow(id));
	}

	@Override
	public ImageResource getPostImage(Long id) {
		return imageService.getImage(postRepository.findByIdOrThrow(id).getPostImage());
	}

	@Override
	public ImageResource getPostThumbnail(Long id) {
		return imageService.getThumbnail(postRepository.findByIdOrThrow(id).getPostImage());
	}

	@Override
	public PostDto editPost(Long id, List<String> tags) {
		Post post = postSecurity.getPostToEdit(id);
		post.clearTags();
		tagService.getOrCreateTagsByNames(tags)//
				.forEach(post::addTag);
		Post saved = postRepository.save(post);
		log.info("Edited post: {}", saved);
		return postMapper.mapToDto(saved);
	}

	@Override
	public void deletePost(Long id) {
		Post post = postSecurity.getPostToDelete(id);
		imageService.deleteImages(post.getPostImage());
		postRepository.delete(post);
	}

	@Override
	public List<PostDto> findPostsByQuery(PostQueryRequest request, PaginationParams pagination) {
		return postQueryExecutorService.executeQuery(request, pagination)//
				.map(postMapper::mapToDto)//
				.collect(Collectors.toList());
	}

}
