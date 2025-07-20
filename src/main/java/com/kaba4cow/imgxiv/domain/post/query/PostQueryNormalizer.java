package com.kaba4cow.imgxiv.domain.post.query;

public interface PostQueryNormalizer {

	NormalizedPostQuery normalizeQuery(String query);

}
