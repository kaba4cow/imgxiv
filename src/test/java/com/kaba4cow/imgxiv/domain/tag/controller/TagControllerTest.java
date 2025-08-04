package com.kaba4cow.imgxiv.domain.tag.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.kaba4cow.imgxiv.domain.category.model.Category;
import com.kaba4cow.imgxiv.domain.category.repository.CategoryRepository;
import com.kaba4cow.imgxiv.domain.tag.model.Tag;
import com.kaba4cow.imgxiv.domain.tag.repository.TagRepository;
import com.kaba4cow.imgxiv.domain.user.policy.UserAuthorities;

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

	@Autowired
	private ObjectMapper objectMapper;

	@SneakyThrows
	@Test
	public void retrievesTag() {
		Category category = saveTestCategory("name");
		String name = "name";
		String description = "description";
		Tag tag = saveTestTag(name, description, category);

		performGetTag(tag.getId())//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").value(tag.getId()))//
				.andExpect(jsonPath("$.name").value(name))//
				.andExpect(jsonPath("$.description").value(description))//
				.andExpect(jsonPath("$.categoryId").value(category.getId()));
	}

	@SneakyThrows
	private ResultActions performGetTag(Long tagId) {
		return mockMvc.perform(get("/api/tags/{id}", tagId));
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.MANAGE_TAGS)
	@Test
	public void editsTagName() {
		Category category = saveTestCategory("name");
		String oldName = "name";
		String newName = "new_name";
		String description = "description";
		Tag tag = saveTestTag(oldName, description, category);

		performEditTag(tag.getId(), Map.of("name", newName))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").value(tag.getId()))//
				.andExpect(jsonPath("$.name").value(newName))//
				.andExpect(jsonPath("$.description").value(description))//
				.andExpect(jsonPath("$.categoryId").value(category.getId()));
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.MANAGE_TAGS)
	@Test
	public void editsTagDescription() {
		Category category = saveTestCategory("name");
		String name = "name";
		String oldDescription = "description";
		String newDescription = "new description";
		Tag tag = saveTestTag(name, oldDescription, category);

		performEditTag(tag.getId(), Map.of("description", newDescription))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").value(tag.getId()))//
				.andExpect(jsonPath("$.name").value(name))//
				.andExpect(jsonPath("$.description").value(newDescription))//
				.andExpect(jsonPath("$.categoryId").value(category.getId()));
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.MANAGE_TAGS)
	@Test
	public void editsTag() {
		Category oldCategory = saveTestCategory("name");
		String oldName = "name";
		String oldDescription = "description";
		Tag tag = saveTestTag(oldName, oldDescription, oldCategory);

		Category newCategory = saveTestCategory("name_new");
		String newName = "new_name";
		String newDescription = "new description";

		performEditTag(tag.getId(), Map.of(//
				"name", newName, //
				"description", newDescription, //
				"category", newCategory.getId()//
		))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").value(tag.getId()))//
				.andExpect(jsonPath("$.name").value(newName))//
				.andExpect(jsonPath("$.description").value(newDescription))//
				.andExpect(jsonPath("$.categoryId").value(newCategory.getId()));
	}

	@SneakyThrows
	private ResultActions performEditTag(Long tagId, Map<String, Object> properties) {
		return mockMvc.perform(patch("/api/tags/{id}", tagId)//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(objectMapper.writeValueAsString(properties)));
	}

	private Tag saveTestTag(String name, String description, Category category) {
		Tag tag = new Tag();
		tag.setCategory(category);
		tag.setName(name);
		tag.setDescription(description);
		return tagRepository.saveAndFlush(tag);
	}

	private Category saveTestCategory(String name) {
		Category category = new Category();
		category.setName(name);
		category.setDescription("description");
		return categoryRepository.saveAndFlush(category);
	}

}
