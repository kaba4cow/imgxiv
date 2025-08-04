package com.kaba4cow.imgxiv.domain.category;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.category.model.Category;
import com.kaba4cow.imgxiv.domain.comment.model.CategoryRepository;

@DataJpaTest
@Transactional
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void existsByName_returnsTrue() {
		String name = "name";

		saveTestCategory(name, "description");

		assertTrue(categoryRepository.existsByName(name));
	}

	@Test
	public void existsByName_returnsFalse() {
		assertFalse(categoryRepository.existsByName("name"));
	}

	@Test
	public void findByIdOrThrow_returnsCategory() {
		Long id = saveTestCategory("name", "description").getId();

		assertNotNull(categoryRepository.findByIdOrThrow(id));
	}

	@Test
	public void findByIdOrThrow_throwsNotFound() {
		assertThrows(NotFoundException.class, () -> categoryRepository.findByIdOrThrow(Long.MAX_VALUE));
	}

	private Category saveTestCategory(String name, String description) {
		Category category = new Category();
		category.setName(name);
		category.setDescription(description);
		return categoryRepository.save(category);
	}

}
