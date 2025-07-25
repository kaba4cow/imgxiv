package com.kaba4cow.imgxiv.domain.category.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoryRepository categoryRepository;

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.CREATE_CATEGORY)
	@Test
	public void createsCategoryWithAuthority() {
		String name = "name";
		String description = "description";

		performCreateCategory(name, description)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.name").isString())//
				.andExpect(jsonPath("$.name").value(name))//
				.andExpect(jsonPath("$.description").isString())//
				.andExpect(jsonPath("$.description").value(description));
	}

	@SneakyThrows
	@Test
	public void doesNotCreateCategoryWithoutAuthority() {
		performCreateCategory("name", "description")//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.CREATE_CATEGORY)
	@Test
	public void doesNotCreateTagWithInvalidName() {
		performCreateCategory("[category.]?", "")//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isBadRequest());
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.CREATE_CATEGORY)
	@Test
	public void doesNotCreateCategoryWithTakenName() {
		String name = "name";
		saveTestCategory(name);
		performCreateCategory(name, "")//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isConflict());
	}

	@SneakyThrows
	@Test
	public void retrievesAllCategories() {
		int numberOfCategories = 3;
		for (int i = 0; i < numberOfCategories; i++)
			saveTestCategory("name" + i);

		performGetAllCategories()//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$", hasSize(numberOfCategories)));
	}

	@SneakyThrows
	@Test
	public void retrievesNoCategories() {
		performGetAllCategories()//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@SneakyThrows
	private ResultActions performCreateCategory(String name, String description) {
		return mockMvc.perform(post("/api/categories")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"name": "%s",
								"description": "%s"
							}
						""".formatted(//
						name, //
						description//
				)));
	}

	@SneakyThrows
	private ResultActions performGetAllCategories() {
		return mockMvc.perform(get("/api/categories/all"));
	}

	private Category saveTestCategory(String name) {
		Category category = new Category();
		category.getNameAndDescription().setName(name);
		category.getNameAndDescription().setDescription("description");
		return categoryRepository.saveAndFlush(category);
	}

}
