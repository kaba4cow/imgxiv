package com.kaba4cow.imgxiv.domain.post.specification;

import com.kaba4cow.imgxiv.domain.post.query.PostQuery;

public interface PostSpecificationFactory {

	PostSpecification createPostSpecification(PostQuery compiledQuery);

}
