package com.kaba4cow.imgxiv.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void registerNewUser() throws Exception {
		String json = """
				{
					"username": "reguser",
					"email": "reg@example.com",
					"password": "testpass512"
				}
				""";

		mockMvc.perform(post("/api/auth/register")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(json))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.username").value("reguser"));
	}

	@Test
	public void registerNewUserAndLogIn() throws Exception {
		String reg = """
				{
				    "username": "loginuser",
				    "email": "login@example.com",
				    "password": "testpass512"
				}
				""";

		mockMvc.perform(post("/api/auth/register")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(reg))//
				.andExpect(status().isOk());

		String login = """
				{
				    "usernameOrEmail": "loginuser",
				    "password": "testpass512"
				}
				""";

		mockMvc.perform(post("/api/auth/login")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(login))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.token").isNotEmpty());
	}

}
