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
		performRegister(//
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
		performRegister(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performRegister(//
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
		performRegister(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performRegister(//
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
		performRegister(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performLogin(//
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
		performRegister(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performLogin(//
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
		performRegister(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performLogin(//
				"wrongUsernameOrEmail", //
				PASSWORD//
		)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	@Test
	public void doesNotAuthenticateByWrongPassword() {
		performRegister(//
				USERNAME, //
				EMAIL, //
				PASSWORD//
		);

		performLogin(//
				USERNAME, //
				"wrongPassword"//
		)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	private ResultActions performRegister(String username, String email, String password) {
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
	private ResultActions performLogin(String usernameOrEmail, String password) {
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
