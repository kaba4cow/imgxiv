package com.kaba4cow.imgxiv.domain.post.query;

import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class NormalizedPostQuery {

	private final List<String> tokens;

	public NormalizedPostQuery(List<String> tokens) {
		this.tokens = Collections.unmodifiableList(tokens);
	}

}
