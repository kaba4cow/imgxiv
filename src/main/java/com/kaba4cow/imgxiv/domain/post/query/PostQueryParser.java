package com.kaba4cow.imgxiv.domain.post.query;

public interface PostQueryParser {

	CompiledPostQuery parse(String query);

}
