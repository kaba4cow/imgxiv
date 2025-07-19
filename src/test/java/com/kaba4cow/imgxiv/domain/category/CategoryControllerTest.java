package com.kaba4cow.imgxiv.domain.category;

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

import lombok.SneakyThrows;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@SneakyThrows
	@WithMockUser(authorities = "create-category")
	@Test
	public void createsCategory() {
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
	@WithMockUser(authorities = "create-category")
	@Test
	public void doesNotCreateCategoryWithTakenName() {
		String name = "name";
		performCreateCategory(name, "");
		performCreateCategory(name, "")//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isConflict());
	}

	@SneakyThrows
	@WithMockUser(authorities = "create-category")
	@Test
	public void retrievesAllCategories() {
		int numberOfCategories = 3;
		for (int i = 0; i < numberOfCategories; i++)
			performCreateCategory("name" + i, "");

		performGetAllCategories()//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$", hasSize(numberOfCategories)));
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

}
