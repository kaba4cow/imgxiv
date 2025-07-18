package com.kaba4cow.imgxiv.domain.category;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class CategoryControllerTest {

	private static final String CREATE_REQUEST = """
				{
					"name": "%s",
					"description": "%s"
				}
			""";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoryRepository categoryRepository;

	@BeforeEach
	public void beforeEach() {
		categoryRepository.deleteAll();
	}

	@WithMockUser(authorities = "create-category")
	@Test
	public void createCategory() throws Exception {
		String name = "name";
		String description = "description";

		mockMvc.perform(post("/api/categories")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(CREATE_REQUEST.formatted(//
						name, //
						description//
				)))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.name").isString())//
				.andExpect(jsonPath("$.name").value(name))//
				.andExpect(jsonPath("$.description").isString())//
				.andExpect(jsonPath("$.description").value(description));
	}

	@Test
	public void getAllCategories() throws Exception {
		mockMvc.perform(get("/api/categories/all"))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray());
	}

}
