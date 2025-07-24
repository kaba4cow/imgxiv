package com.kaba4cow.imgxiv.domain.post.service;

import java.util.stream.Stream;

import com.kaba4cow.imgxiv.common.dto.PaginationParams;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;

public interface PostQueryExecutorService {

	Stream<Post> executeQuery(PostQueryRequest request, PaginationParams pagination);

}
