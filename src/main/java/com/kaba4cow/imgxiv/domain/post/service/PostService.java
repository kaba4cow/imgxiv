package com.kaba4cow.imgxiv.domain.post.service;

import java.util.List;

import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.user.User;

public interface PostService {

	PostDto create(PostCreateRequest request, User author);

	List<PostDto> findByQuery(PostQueryRequest request);

}
