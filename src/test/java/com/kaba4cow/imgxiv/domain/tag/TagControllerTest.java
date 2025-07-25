package com.kaba4cow.imgxiv.domain.tag;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;

import lombok.SneakyThrows;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class TagControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TagRepository tagRepository;

	@SneakyThrows
	@Test
	public void retrievesTag() {
		Category category = saveTestCategory("name");
		String name = "name";
		String description = "description";
		Tag tag = saveTestTag(name, description, category);

		performGetTag(tag.getId())//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.name").isString())//
				.andExpect(jsonPath("$.name").value(name))//
				.andExpect(jsonPath("$.description").isString())//
				.andExpect(jsonPath("$.description").value(description))//
				.andExpect(jsonPath("$.categoryId").isNumber())//
				.andExpect(jsonPath("$.categoryId").value(category.getId()));
	}

	@SneakyThrows
	private ResultActions performGetTag(Long tagId) {
		return mockMvc.perform(get("/api/tags/{id}", tagId));
	}

	private Tag saveTestTag(String name, String description, Category category) {
		Tag tag = new Tag();
		tag.setCategory(category);
		tag.getNameAndDescription().setName(name);
		tag.getNameAndDescription().setDescription(description);
		return tagRepository.saveAndFlush(tag);
	}

	private Category saveTestCategory(String name) {
		Category category = new Category();
		category.getNameAndDescription().setName(name);
		category.getNameAndDescription().setDescription("description");
		return categoryRepository.saveAndFlush(category);
	}

}
