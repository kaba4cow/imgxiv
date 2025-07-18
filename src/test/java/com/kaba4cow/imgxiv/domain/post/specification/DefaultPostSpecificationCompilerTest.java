package com.kaba4cow.imgxiv.domain.post.specification;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DefaultPostSpecificationCompilerTest {

	private static final DefaultPostSpecificationCompiler compiler = new DefaultPostSpecificationCompiler();

	private static DefaultPostSpecification specification;

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
		assertTrue(specification.getRequiredTags().containsAll(List.of(tags)));
	}

	private static void assertRequiresNothing() {
		assertTrue(specification.getRequiredTags().isEmpty());
	}

	private static void assertDoesNotRequire(String... tags) {
		assertFalse(specification.getRequiredTags().containsAll(List.of(tags)));
	}

	private static void assertExcludes(String... tags) {
		assertTrue(specification.getExcludedTags().containsAll(List.of(tags)));
	}

	private static void assertExcludesNothing() {
		assertTrue(specification.getExcludedTags().isEmpty());
	}

	private static void assertDoesNotExclude(String... tags) {
		assertFalse(specification.getExcludedTags().containsAll(List.of(tags)));
	}

	private static void compile(String query) {
		specification = (DefaultPostSpecification) compiler.compile(query);
	}

}
