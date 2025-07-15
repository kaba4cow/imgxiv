package com.kaba4cow.imgxiv.domain.category;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private String token;

	@BeforeAll
	void setup() throws Exception {
		String reg = """
				{
					"username": "testuser",
					"email": "test@example.com",
					"password": "testpass512"
				}
				""";

		mockMvc.perform(post("/api/auth/register")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(reg))//
				.andExpect(status().isOk());

		String login = """
				{
					"usernameOrEmail": "testuser",
					"password": "testpass512"
				}
				""";

		MvcResult result = mockMvc.perform(post("/api/auth/login")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(login))//
				.andExpect(status().isOk())//
				.andReturn();

		String json = result.getResponse().getContentAsString();
		JsonNode node = new ObjectMapper().readTree(json);
		token = node.get("token").asText();
	}

	@Test
	public void createCategory() throws Exception {
		String json = """
				{
					"name": "category-name",
					"description": "category-description"
				}
				""";

		mockMvc.perform(post("/api/categories")//
				.header("Authorization", "Bearer " + token)//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(json))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.name").value("category-name"));
	}

	@Test
	public void getAllCategories() throws Exception {
		mockMvc.perform(get("/api/categories")//
				.header("Authorization", "Bearer " + token))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray());
	}

}
