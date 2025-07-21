package com.kaba4cow.imgxiv.domain.post.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostEditRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.user.User;

public interface PostService {

	PostDto createPost(PostCreateRequest request, MultipartFile image, User author);

	PostDto editPost(PostEditRequest request);

	void deletePost(Long id);

	List<PostDto> findPostsByQuery(PostQueryRequest request);

}
