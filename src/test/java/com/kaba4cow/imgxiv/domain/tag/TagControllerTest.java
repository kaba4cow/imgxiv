package com.kaba4cow.imgxiv.domain.tag;

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

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class TagControllerTest {

	private static final String CREATE_REQUEST = """
				{
					"name": "%s",
					"description": "%s",
					"categoryId": %s
				}
			""";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TagRepository tagRepository;

	@BeforeEach
	public void beforeEach() {
		tagRepository.deleteAll();
		categoryRepository.deleteAll();
	}

	@WithMockUser(authorities = "create-tag")
	@Test
	public void createTag() throws Exception {
		String name = "name";
		String description = "description";
		Long categoryId = createTestCategory().getId();

		mockMvc.perform(post("/api/tags")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(CREATE_REQUEST.formatted(//
						name, //
						description, //
						categoryId//
				)))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.name").isString())//
				.andExpect(jsonPath("$.name").value(name))//
				.andExpect(jsonPath("$.description").isString())//
				.andExpect(jsonPath("$.description").value(description))//
				.andExpect(jsonPath("$.categoryId").isNumber())//
				.andExpect(jsonPath("$.categoryId").value(categoryId));
	}

	@Test
	public void getTagsByCategory() throws Exception {
		Category category = createTestCategory();
		createTestTag(category);

		mockMvc.perform(get("/api/tags")//
				.param("categoryId", category.getId().toString()))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$").isNotEmpty());
	}

	private Category createTestCategory() {
		Category category = new Category();
		category.getNameAndDescription().setName("name");
		category.getNameAndDescription().setDescription("description");
		return categoryRepository.save(category);
	}

	private Tag createTestTag(Category category) {
		Tag tag = new Tag();
		tag.getNameAndDescription().setName("name");
		tag.getNameAndDescription().setDescription("description");
		tag.setCategory(category);
		return tagRepository.save(tag);
	}

}
