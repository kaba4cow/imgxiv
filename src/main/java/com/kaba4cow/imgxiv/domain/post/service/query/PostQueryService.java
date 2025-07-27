package com.kaba4cow.imgxiv.domain.post.service.query;

import com.kaba4cow.imgxiv.domain.post.query.PostQuery;

public interface PostQueryService {

	PostQuery getCompiledQuery(String query);

}
