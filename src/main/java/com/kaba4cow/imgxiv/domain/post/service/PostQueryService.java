package com.kaba4cow.imgxiv.domain.post.service;

import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;

public interface PostQueryService {

	CompiledPostQuery getPostQuery(String query);

}
