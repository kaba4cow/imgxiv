package com.kaba4cow.imgxiv.domain.tag;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.category.service.CategoryService;

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
	private CategoryService categoryService;

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
		return mockMvc.perform(get("/api/tags")//
				.param("tagId", tagId.toString()));
	}

	@SneakyThrows
	@Test
	public void retrievesCategorizedTags() {
		Map<Category, Integer> categoriesWithNumbersOfTags = Map.of(//
				saveTestCategory("category1"), 3, //
				saveTestCategory("category2"), 5, //
				saveTestCategory("category3"), 7, //
				saveTestCategory("category4"), 11 //
		);
		generateTagsForCategories(categoriesWithNumbersOfTags);
		for (Map.Entry<Category, Integer> entry : categoriesWithNumbersOfTags.entrySet()) {
			Category category = entry.getKey();
			int numberOfTags = entry.getValue();
			performGetCategorizedTags(category.getId())//
					.andExpect(status().isOk())//
					.andExpect(jsonPath("$").isArray())//
					.andExpect(jsonPath("$", hasSize(numberOfTags)));
		}
	}

	private void generateTagsForCategories(Map<Category, Integer> categoriesWithNumbersOfTags) {
		for (Map.Entry<Category, Integer> entry : categoriesWithNumbersOfTags.entrySet()) {
			Category category = entry.getKey();
			int numberOfTags = entry.getValue();
			for (int i = 0; i < numberOfTags; i++)
				saveTestTag(category.getNameAndDescription().getName() + "_tag" + i, "description", category);
		}
	}

	@SneakyThrows
	private ResultActions performGetCategorizedTags(Long categoryId) {
		return mockMvc.perform(get("/api/tags/categorized")//
				.param("categoryId", categoryId.toString()));
	}

	@SneakyThrows
	@Test
	public void retrievesUncategorizedTags() {
		int numberOfTags = 10;
		for (int i = 0; i < numberOfTags; i++)
			saveTestTag("tag" + i, "description");
		performGetUncategorizedTags()//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$", hasSize(numberOfTags)));
	}

	@SneakyThrows
	private ResultActions performGetUncategorizedTags() {
		return mockMvc.perform(get("/api/tags/uncategorized"));
	}

	private Tag saveTestTag(String name, String description) {
		return saveTestTag(name, description, categoryService.getDefaultCategory());
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
