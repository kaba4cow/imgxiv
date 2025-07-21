package com.kaba4cow.imgxiv.domain.post.service.query;

import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;
import com.kaba4cow.imgxiv.domain.post.query.NormalizedPostQuery;

public interface PostQueryService {

	CompiledPostQuery getCompiledQuery(NormalizedPostQuery normalizedQuery);

}
