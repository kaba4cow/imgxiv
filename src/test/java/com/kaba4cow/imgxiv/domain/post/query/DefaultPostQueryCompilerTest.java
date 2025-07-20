package com.kaba4cow.imgxiv.domain.post.query;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DefaultPostQueryCompilerTest {

	private static final DefaultPostQueryCompiler compiler = new DefaultPostQueryCompiler();

	private static PostQuery postQuery;

	@Test
	void compilesRequired() {
		compile("a b");

		assertRequires("a", "b");
		assertExcludesNothing();
	}

	@Test
	void compilesExcluded() {
		compile("!a !b");

		assertRequiresNothing();
		assertExcludes("a", "b");
	}

	@Test
	void compilesRequiredAndExcluded() {
		compile("a !b c !d");

		assertRequires("a", "c");
		assertExcludes("b", "d");
	}

	@Test
	void compilesMultipleNegations() {
		compile("a !b !!c !!!d");

		assertRequires("a", "c");
		assertExcludes("b", "d");
	}

	@Test
	void compilesMultipleSpaces() {
		compile("a  \t  b !  !\n   c");

		assertRequires("a", "b", "c");
		assertDoesNotRequire(" ", "\t");
		assertDoesNotExclude(" ", "\n");
	}

	@Test
	void compilesNoTags() {
		compile("!!!!!!!!!");

		assertRequiresNothing();
		assertExcludesNothing();
	}

	@Test
	void compilesDuplicateTags() {
		compile("a !a");

		assertDoesNotRequire("a");
		assertExcludes("a");
	}

	private static void assertRequires(String... tags) {
		assertTrue(postQuery.getRequiredTags().containsAll(List.of(tags)));
	}

	private static void assertRequiresNothing() {
		assertTrue(postQuery.getRequiredTags().isEmpty());
	}

	private static void assertDoesNotRequire(String... tags) {
		assertFalse(postQuery.getRequiredTags().containsAll(List.of(tags)));
	}

	private static void assertExcludes(String... tags) {
		assertTrue(postQuery.getExcludedTags().containsAll(List.of(tags)));
	}

	private static void assertExcludesNothing() {
		assertTrue(postQuery.getExcludedTags().isEmpty());
	}

	private static void assertDoesNotExclude(String... tags) {
		assertFalse(postQuery.getExcludedTags().containsAll(List.of(tags)));
	}

	private static void compile(String query) {
		postQuery = compiler.compile(query);
	}

}
