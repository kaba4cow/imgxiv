package com.kaba4cow.imgxiv.domain.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostMapper;
import com.kaba4cow.imgxiv.domain.post.dto.PostPreviewDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.tag.service.TagService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.util.PersistLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {

	private final PostRepository postRepository;

	private final PostQueryExecutorService postQueryExecutorService;

	private final TagService tagService;

	private final PostMapper postMapper;

	@Override
	public PostDto create(PostCreateRequest request, User author) {
		Post post = createPost(request, author);
		return postMapper.mapToDto(post);
	}

	private Post createPost(PostCreateRequest request, User author) {
		Post post = new Post();
		post.setAuthor(author);
		tagService.findByIdsOrThrow(request.getTagIds())//
				.forEach(post::addTag);
		return PersistLog.log(postRepository.save(post));
	}

	@Override
	public List<PostPreviewDto> findByQuery(PostQueryRequest request) {
		return postQueryExecutorService.executeQuery(request)//
				.map(postMapper::mapToPreviewDto)//
				.collect(Collectors.toList());
	}

}
