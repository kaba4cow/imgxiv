package com.kaba4cow.imgxiv.domain.post.service.query;

import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;

public interface PostQueryService {

	CompiledPostQuery getCompiledQuery(String query);

}
