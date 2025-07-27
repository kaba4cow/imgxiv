package com.kaba4cow.imgxiv.domain.post.service;

import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;

public interface PostSearchService {

	Stream<Post> searchPosts(PostQueryRequest request, Pageable pageable);

}
