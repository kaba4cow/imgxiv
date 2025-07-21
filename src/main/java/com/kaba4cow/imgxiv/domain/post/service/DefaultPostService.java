package com.kaba4cow.imgxiv.domain.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostMapper;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.factory.PostFactory;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {

	private final PostQueryExecutorService postQueryExecutorService;

	private final PostFactory postFactory;

	private final PostMapper postMapper;

	@Override
	public PostDto createPost(PostCreateRequest request, User author) {
		return postMapper.mapToDto(postFactory.createPost(request, author));
	}

	@Override
	public List<PostDto> findPostsByQuery(PostQueryRequest request) {
		return postQueryExecutorService.executeQuery(request)//
				.map(postMapper::mapToDto)//
				.collect(Collectors.toList());
	}

}
