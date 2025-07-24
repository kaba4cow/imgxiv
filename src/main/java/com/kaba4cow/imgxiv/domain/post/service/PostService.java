package com.kaba4cow.imgxiv.domain.post.service;

import java.util.List;

import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.image.ImageResource;

public interface PostService {

	PostDto createPost(PostCreateRequest request, User author);

	PostDto getPost(Long id);

	ImageResource getPostImage(Long id);

	ImageResource getPostThumbnail(Long id);

	PostDto editPost(Long id, List<String> tags);

	void deletePost(Long id);

	List<PostDto> findPostsByQuery(PostQueryRequest request);

}
