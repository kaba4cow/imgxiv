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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class AuthControllerTest {

	private static final String USERNAME = "username";
	private static final String EMAIL = "test@example.com";
	private static final String PASSWORD = "password1234";

	@Autowired
	private MockMvc mockMvc;

	@SneakyThrows
	@Test
	public void registersNewUser() {
		performRegisterUser(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.username").value(USERNAME))//
				.andExpect(jsonPath("$.email").value(EMAIL));
	}

	@SneakyThrows
	@Test
	public void doesNotRegisterWithTakenUsername() {
		performRegisterUser(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performRegisterUser(//
				USERNAME, //
				"new_" + EMAIL, //
				PASSWORD//
		)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isConflict());
	}

	@SneakyThrows
	@Test
	public void doesNotRegisterWithTakenEmail() {
		performRegisterUser(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performRegisterUser(//
				"new_" + USERNAME, //
				EMAIL, //
				PASSWORD//
		)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isConflict());
	}

	@SneakyThrows
	@Test
	public void authenticatesByUsername() {
		performRegisterUser(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performAuthenticateUser(//
				USERNAME, //
				PASSWORD//
		)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.token").isNotEmpty())//
				.andExpect(jsonPath("$.user.username").value(USERNAME))//
				.andExpect(jsonPath("$.user.email").value(EMAIL));
	}

	@SneakyThrows
	@Test
	public void authenticatesByEmail() {
		performRegisterUser(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performAuthenticateUser(//
				EMAIL, //
				PASSWORD//
		)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.token").isNotEmpty())//
				.andExpect(jsonPath("$.user.username").value(USERNAME))//
				.andExpect(jsonPath("$.user.email").value(EMAIL));
	}

	@SneakyThrows
	@Test
	public void doesNotAuthenticateByWrongUsernameOrEmail() {
		performRegisterUser(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performAuthenticateUser(//
				"wrongUsernameOrEmail", //
				PASSWORD//
		)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	@Test
	public void doesNotAuthenticateByWrongPassword() {
		performRegisterUser(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performAuthenticateUser(//
				USERNAME, //
				"wrongPassword"//
		)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	private ResultActions performRegisterUser(String username, String email, String password) {
		return mockMvc.perform(post("/api/auth/register")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"username": "%s",
								"email": "%s",
								"password": "%s"
							}
						""".formatted(//
						username, //
						email, //
						password//
				)));
	}

	@SneakyThrows
	private ResultActions performAuthenticateUser(String usernameOrEmail, String password) {
		return mockMvc.perform(post("/api/auth/login")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
							    "usernameOrEmail": "%s",
							    "password": "%s"
							}
						""".formatted(//
						usernameOrEmail, //
						password//
				)));
	}

}
