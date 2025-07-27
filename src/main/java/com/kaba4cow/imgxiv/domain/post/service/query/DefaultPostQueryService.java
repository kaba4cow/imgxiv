package com.kaba4cow.imgxiv.domain.post.service.query;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.query.PostQuery;
import com.kaba4cow.imgxiv.domain.post.query.PostQueryParser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostQueryService implements PostQueryService {

	private final PostQueryParser postQueryParser;

	@Override
	public PostQuery getCompiledQuery(String query) {
		return postQueryParser.parse(query);
	}

}
