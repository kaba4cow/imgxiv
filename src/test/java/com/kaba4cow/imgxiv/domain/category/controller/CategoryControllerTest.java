package com.kaba4cow.imgxiv.domain.category.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

import com.fasterxml.jackson.databind.ObjectMapper;
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

	@Autowired
	private ObjectMapper objectMapper;

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
		saveTestCategory(name, "description");
		performCreateCategory(name, "")//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isConflict());
	}

	@SneakyThrows
	private ResultActions performCreateCategory(String name, String description) {
		return mockMvc.perform(post("/api/categories")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(objectMapper.writeValueAsString(Map.of(//
						"name", name, //
						"description", description//
				))));
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.MANAGE_CATEGORIES)
	@Test
	public void editsCategoryWithAuthority() {
		String oldName = "name";
		String oldDescription = "description";
		Category category = saveTestCategory(oldName, oldDescription);

		String newName = "name_new";
		String newDescription = "description_new";

		performEditCategory(category.getId(), newName, newDescription)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").value(category.getId()))//
				.andExpect(jsonPath("$.name").value(newName))//
				.andExpect(jsonPath("$.description").value(newDescription));
	}

	@SneakyThrows
	private ResultActions performEditCategory(Long id, String name, String description) {
		return mockMvc.perform(patch("/api/categories/{id}", id)//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(objectMapper.writeValueAsString(Map.of(//
						"name", name, //
						"description", description//
				))));
	}

	@SneakyThrows
	@Test
	public void retrievesAllCategories() {
		int numberOfCategories = 3;
		for (int i = 0; i < numberOfCategories; i++)
			saveTestCategory("name" + i, "description");

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
	private ResultActions performGetAllCategories() {
		return mockMvc.perform(get("/api/categories/all"));
	}

	private Category saveTestCategory(String name, String description) {
		Category category = new Category();
		category.setName(name);
		category.setDescription(description);
		return categoryRepository.saveAndFlush(category);
	}

}
