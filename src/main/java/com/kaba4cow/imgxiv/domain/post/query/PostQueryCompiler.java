package com.kaba4cow.imgxiv.domain.post.query;

public interface PostQueryCompiler {

	CompiledPostQuery compile(String query);

}
