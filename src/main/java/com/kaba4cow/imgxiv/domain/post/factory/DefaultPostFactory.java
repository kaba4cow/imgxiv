package com.kaba4cow.imgxiv.domain.post.factory;

import com.kaba4cow.imgxiv.common.annotation.Factory;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Factory
public class DefaultPostFactory implements PostFactory {

	private final PostRepository postRepository;

	private final TagRepository tagRepository;

	@Override
	public Post createPost(PostCreateRequest request, User author) {
		Post post = new Post();
		post.setAuthor(author);
		tagRepository.findByIdsOrThrow(request.getTagIds())//
				.forEach(post::addTag);
		Post saved = postRepository.save(post);
		log.info("Created new post: {}", saved);
		return saved;
	}

}
