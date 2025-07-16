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

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class TagControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TagRepository tagRepository;

	@BeforeEach
	public void prepare() {
		categoryRepository.deleteAll();
		tagRepository.deleteAll();
	}

	@WithMockUser(authorities = "create-tag")
	@Test
	public void createTag() throws Exception {
		Category category = createTestCategory();
		mockMvc.perform(post("/api/tags")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"name": "tag-name",
								"description": "tag-description",
								"categoryId": %s
							}
						"""//
						.formatted(category.getId())))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.name").value("tag-name"))//
				.andExpect(jsonPath("$.description").value("tag-description"))//
				.andExpect(jsonPath("$.categoryId").value(category.getId()));
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
		category.getNameAndDescription().setName("category-name");
		category.getNameAndDescription().setDescription("category-description");
		return categoryRepository.save(category);
	}

	private Tag createTestTag(Category category) {
		Tag tag = new Tag();
		tag.getNameAndDescription().setName("tag-name");
		tag.getNameAndDescription().setDescription("tag-description");
		tag.setCategory(category);
		return tagRepository.save(tag);
	}

}
