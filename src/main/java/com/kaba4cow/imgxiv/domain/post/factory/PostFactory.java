package com.kaba4cow.imgxiv.domain.post.factory;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.user.User;

public interface PostFactory {

	Post createPost(PostCreateRequest request, User author);

}
