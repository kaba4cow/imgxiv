package com.kaba4cow.imgxiv.domain.post.query;

import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

public interface PostQueryParser {

	PostSpecification parseQuery(String query);

}
