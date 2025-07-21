package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;
import com.kaba4cow.imgxiv.domain.post.query.NormalizedPostQuery;
import com.kaba4cow.imgxiv.domain.post.query.PostQueryCompiler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostQueryService implements PostQueryService {

	private final PostQueryCompiler postQueryCompiler;

	@Override
	public CompiledPostQuery getCompiledQuery(NormalizedPostQuery normalizedQuery) {
		return postQueryCompiler.compileQuery(normalizedQuery);
	}

}
