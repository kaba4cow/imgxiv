package com.kaba4cow.imgxiv.domain.post.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostMapper;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.post.repository.PostRepository;
import com.kaba4cow.imgxiv.domain.post.security.PostSecurity;
import com.kaba4cow.imgxiv.domain.tag.service.TagService;
import com.kaba4cow.imgxiv.domain.user.model.User;
import com.kaba4cow.imgxiv.image.ImageResource;
import com.kaba4cow.imgxiv.image.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {

	private final PostSearchService postSearchService;

	private final PostRepository postRepository;

	private final TagService tagService;

	private final ImageService imageService;

	private final PostSecurity postSecurity;

	private final PostMapper postMapper;

	@Override
	public PostDto createPost(PostCreateRequest request, User author) {
		Post post = Post.builder()//
				.author(author)//
				.postImage(imageService.createImages(request.getImage()))//
				.build();
		tagService.getOrCreateTagsByNames(request.getTags()).forEach(post::addTag);
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
		log.info("Updated post: {}", saved);
		return postMapper.mapToDto(saved);
	}

	@Override
	public void deletePost(Long id) {
		Post post = postSecurity.getPostToDelete(id);
		imageService.deleteImages(post.getPostImage());
		postRepository.delete(post);
		log.info("Deleted post: {}", post);
	}

	@Override
	public List<PostDto> findPostsByQuery(PostQueryRequest request, Pageable pageable) {
		return postSearchService.searchPosts(request, pageable)//
				.map(postMapper::mapToDto)//
				.toList();
	}

}
