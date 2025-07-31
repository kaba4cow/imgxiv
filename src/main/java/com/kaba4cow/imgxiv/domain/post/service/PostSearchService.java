package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;

public interface PostSearchService {

	Page<Post> searchPosts(PostQueryRequest request, Pageable pageable);

}
