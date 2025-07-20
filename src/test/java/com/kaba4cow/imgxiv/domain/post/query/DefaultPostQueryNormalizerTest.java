package com.kaba4cow.imgxiv.domain.post.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.RequiredArgsConstructor;

public class DefaultPostQueryNormalizerTest {

	private static final DefaultPostQueryNormalizer normalizer = new DefaultPostQueryNormalizer();

	@Test
	void normalizesRequired() {
		assertThat("a b")//
				.normalizedTo("a", "b");
	}

	@Test
	void normalizedExcluded() {
		assertThat("!a !b")//
				.normalizedTo("!a", "!b");
	}

	@Test
	void normalizesRequiredAndExcluded() {
		assertThat("a !b c !d")//
				.normalizedTo("a", "!b", "c", "!d");
	}

	@Test
	void normalizesMultipleNegations() {
		assertThat("a !b !!c !!!d")//
				.normalizedTo("a", "!b", "!!c", "!!!d");
	}

	@Test
	void ignoresMultipleSpaces() {
		assertThat("a  \t  b \n   c")//
				.normalizedTo("a", "b", "c")//
				.ignored(" ", "\t", "", "\n");
	}

	@Test
	void ignoresEmptyTags() {
		assertThat("a  !  !!!  c")//
				.normalizedTo("a", "c")//
				.ignored(" ", "", "!", "!!");
	}

	@Test
	void normalizesDuplicateTags() {
		assertThat("a !a")//
				.normalizedTo("a", "!a");
	}

	private static NormalizedPostQueryAssertor assertThat(String query) {
		return new NormalizedPostQueryAssertor(normalizer.normalizeQuery(query));
	}

	@RequiredArgsConstructor
	private static class NormalizedPostQueryAssertor {

		private final NormalizedPostQuery normalizedQuery;

		private NormalizedPostQueryAssertor normalizedTo(String... tags) {
			assertEquals(normalizedQuery.getTokens(), List.of(tags));
			return this;
		}

		private NormalizedPostQueryAssertor ignored(String... tags) {
			assertFalse(containsAny(tags));
			return this;
		}

		private boolean containsAny(String... tags) {
			for (String tag : tags)
				if (normalizedQuery.getTokens().contains(tag))
					return true;
			return false;
		}

	}

}
