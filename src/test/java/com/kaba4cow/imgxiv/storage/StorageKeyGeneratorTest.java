package com.kaba4cow.imgxiv.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kaba4cow.imgxiv.domain.embeddable.PostImage;

import jakarta.persistence.Column;
import lombok.SneakyThrows;

public class StorageKeyGeneratorTest {

	private final StorageKeyGenerator generator = new StorageKeyGenerator();

	@Test
	public void keyLengthNotGreaterThanColumnLength() {
		assertTrue(generator.generateKey("filename").length() <= getKeyColumnLength());
	}

	@SneakyThrows
	private static int getKeyColumnLength() {
		return PostImage.class//
				.getDeclaredField("storageKey")//
				.getAnnotation(Column.class)//
				.length();
	}

	@Test
	public void generatesUniqueKeys() {
		int stringCount = 1024;
		List<String> uniques = generateStrings(stringCount).stream()//
				.map(generator::generateKey)//
				.distinct()//
				.toList();
		assertEquals(stringCount, uniques.size());
		uniques.forEach(System.out::println);
	}

	private static List<String> generateStrings(int stringCount) {
		List<String> strings = new ArrayList<>();
		String string = "a".repeat(256);
		for (int i = 0; i < stringCount; i++)
			strings.add(string);
		return strings;
	}

}
