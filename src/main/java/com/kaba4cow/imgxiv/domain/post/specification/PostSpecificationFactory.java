package com.kaba4cow.imgxiv.domain.post.specification;

import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;

public interface PostSpecificationFactory {

	PostSpecification createPostSpecification(CompiledPostQuery compiledQuery);

}
