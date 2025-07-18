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
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class AuthControllerTest {

	private static final String REGISTER_REQUEST = """
				{
					"username": "%s",
					"email": "%s",
					"password": "%s"
				}
			""";

	private static final String LOGIN_REQUEST = """
				{
				    "usernameOrEmail": "%s",
				    "password": "%s"
				}
			""";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void registerAndLogin() throws Exception {
		String username = "username";
		String email = "test@example.com";
		String password = "password1234";

		mockMvc.perform(post("/api/auth/register")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(REGISTER_REQUEST.formatted(//
						username, //
						email, //
						password//
				)))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.username").value(username))//
				.andExpect(jsonPath("$.email").value(email));

		mockMvc.perform(post("/api/auth/login")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(LOGIN_REQUEST.formatted(//
						username, //
						password//
				)))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.token").isNotEmpty())//
				.andExpect(jsonPath("$.user.username").value(username))//
				.andExpect(jsonPath("$.user.email").value(email));

		mockMvc.perform(post("/api/auth/login")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(LOGIN_REQUEST.formatted(//
						email, //
						password//
				)))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.token").isNotEmpty())//
				.andExpect(jsonPath("$.user.username").value(username))//
				.andExpect(jsonPath("$.user.email").value(email));
	}

}
