package com.kaba4cow.imgxiv.domain.tag;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;

@DataJpaTest
@Transactional
public class TagRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TagRepository tagRepository;

	@Test
	public void existsByName_returnsTrue() {
		String name = "name";

		Category category = saveTestCategory("name", "description");
		saveTestTag(name, "description", category);

		assertTrue(tagRepository.existsByName(name));
	}

	@Test
	public void existsByName_returnsFalse() {
		assertFalse(tagRepository.existsByName("name"));
	}

	@Test
	public void findByName_returnsPresent() {
		String name = "name";

		Category category = saveTestCategory("name", "description");
		saveTestTag(name, "description", category);

		assertTrue(tagRepository.findByName(name).isPresent());
	}

	@Test
	public void findByName_returnsEmpty() {
		assertTrue(tagRepository.findByName("name").isEmpty());
	}

	@Test
	public void findByCategory_returnsEmptyList() {
		Category category = saveTestCategory("name", "description");

		assertTrue(tagRepository.findByCategory(category).isEmpty());
	}

	@Test
	public void findByCategory_returnsNonEmptyList() {
		Category category = saveTestCategory("name", "description");
		saveTestTag("name", "description", category);

		assertFalse(tagRepository.findByCategory(category).isEmpty());
	}

	@Test
	public void findByCategory_returnsCorrectTags() {
		Category category1 = saveTestCategory("c1", "description");
		Tag category1Tag1 = saveTestTag("c1t1", "description", category1);
		Tag category1Tag2 = saveTestTag("c1t2", "description", category1);

		Category category2 = saveTestCategory("c2", "description");
		Tag category2Tag1 = saveTestTag("c2t1", "description", category2);
		Tag category2Tag2 = saveTestTag("c2t2", "description", category2);

		List<Tag> category1Tags = tagRepository.findByCategory(category1);
		assertTrue(category1Tags.contains(category1Tag1));
		assertTrue(category1Tags.contains(category1Tag2));
		assertFalse(category1Tags.contains(category2Tag1));
		assertFalse(category1Tags.contains(category2Tag2));
	}

	@Test
	public void findByIdOrThrow_returnsTag() {
		Category category = saveTestCategory("name", "description");
		Long id = saveTestTag("name", "description", category).getId();

		assertNotNull(tagRepository.findByIdOrThrow(id));
	}

	@Test
	public void findByIdOrThrow_throwsNotFound() {
		assertThrows(NotFoundException.class, () -> tagRepository.findByIdOrThrow(Long.MAX_VALUE));
	}

	private Tag saveTestTag(String name, String description, Category category) {
		Tag tag = new Tag();
		tag.setCategory(category);
		tag.getNameAndDescription().setName(name);
		tag.getNameAndDescription().setDescription(description);
		return tagRepository.save(tag);
	}

	private Category saveTestCategory(String name, String description) {
		Category category = new Category();
		category.getNameAndDescription().setName(name);
		category.getNameAndDescription().setDescription(description);
		return categoryRepository.save(category);
	}

}
