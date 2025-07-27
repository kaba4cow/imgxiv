package com.kaba4cow.imgxiv.domain.post.service.specification;

import com.kaba4cow.imgxiv.domain.post.query.PostQuery;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

public interface PostSpecificationService {

	PostSpecification getPostSpecification(PostQuery postQuery);

}
