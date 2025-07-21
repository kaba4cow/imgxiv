package com.kaba4cow.imgxiv.domain.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostMapper;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {

	private final PostRepository postRepository;

	private final PostQueryExecutorService postQueryExecutorService;

	private final TagRepository tagRepository;

	private final PostMapper postMapper;

	@Override
	public PostDto create(PostCreateRequest request, User author) {
		Post post = new Post();
		post.setAuthor(author);
		tagRepository.findByIdsOrThrow(request.getTagIds())//
				.forEach(post::addTag);
		Post saved = postRepository.save(post);
		log.info("Created new post: {}", saved);
		return postMapper.mapToDto(saved);
	}

	@Override
	public List<PostDto> findByQuery(PostQueryRequest request) {
		return postQueryExecutorService.executeQuery(request)//
				.map(postMapper::mapToDto)//
				.collect(Collectors.toList());
	}

}
