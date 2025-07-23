package com.kaba4cow.imgxiv.domain.tag;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.user.UserAuthorities;

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
	@WithMockUser(authorities = UserAuthorities.CREATE_TAG)
	@Test
	public void createsTagWithAuthority() {
		String name = "name";
		String description = "description";
		Long categoryId = saveTestCategory("name").getId();

		performCreateTag(name, description, categoryId)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.name").isString())//
				.andExpect(jsonPath("$.name").value(name))//
				.andExpect(jsonPath("$.description").isString())//
				.andExpect(jsonPath("$.description").value(description))//
				.andExpect(jsonPath("$.categoryId").isNumber())//
				.andExpect(jsonPath("$.categoryId").value(categoryId));
	}

	@SneakyThrows
	@Test
	public void doesNotCreateTagWithoutAuthority() {
		performCreateTag("name", "", saveTestCategory("name").getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.CREATE_TAG)
	@Test
	public void doesNotCreateTagWithInvalidName() {
		performCreateTag("[tag.]?", "", saveTestCategory("category").getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isBadRequest());
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.CREATE_TAG)
	@Test
	public void doesNotCreateTagWithTakenName() {
		Category category = saveTestCategory("category");
		String name = "name";
		saveTestTag(name, category);
		performCreateTag(name, "", category.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isConflict());
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.CREATE_TAG)
	@Test
	public void doesNotCreateTagWithCategoryNotFound() {
		performCreateTag("name", "", 0l)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	@Test
	public void retrievesTagsByCategory() {
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
			performGetTagsByCategory(category.getId())//
					.andExpect(status().isOk())//
					.andExpect(jsonPath("$").isArray())//
					.andExpect(jsonPath("$", hasSize(numberOfTags)));
		}
	}

	@SneakyThrows
	@Test
	public void retrievesAllTags() {
		int numberOfTags = 5;
		generateTagsForCategories(Map.of(saveTestCategory("name"), numberOfTags));

		performGetAllTags()//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$", hasSize(numberOfTags)));
	}

	@SneakyThrows
	@Test
	public void retrievesNoTags() {
		performGetAllTags()//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@SneakyThrows
	private ResultActions performCreateTag(String name, String description, Long categoryId) {
		return mockMvc.perform(post("/api/tags")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"name": "%s",
								"description": "%s",
								"categoryId": %s
							}
						""".formatted(//
						name, //
						description, //
						categoryId//
				)));
	}

	@SneakyThrows
	private ResultActions performGetTagsByCategory(Long categoryId) {
		return mockMvc.perform(get("/api/tags")//
				.param("categoryId", categoryId.toString()));
	}

	@SneakyThrows
	private ResultActions performGetAllTags() {
		return mockMvc.perform(get("/api/tags/all"));
	}

	private void generateTagsForCategories(Map<Category, Integer> categoriesWithNumbersOfTags) {
		for (Map.Entry<Category, Integer> entry : categoriesWithNumbersOfTags.entrySet()) {
			Category category = entry.getKey();
			int numberOfTags = entry.getValue();
			for (int i = 0; i < numberOfTags; i++)
				saveTestTag(category.getNameAndDescription().getName() + "_tag" + i, category);
		}
	}

	private Tag saveTestTag(String name, Category category) {
		Tag tag = new Tag();
		tag.setCategory(category);
		tag.getNameAndDescription().setName(name);
		tag.getNameAndDescription().setDescription("description");
		return tagRepository.saveAndFlush(tag);
	}

	private Category saveTestCategory(String name) {
		Category category = new Category();
		category.getNameAndDescription().setName(name);
		category.getNameAndDescription().setDescription("description");
		return categoryRepository.saveAndFlush(category);
	}

}
