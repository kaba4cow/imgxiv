package com.kaba4cow.imgxiv.domain.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class UserControllerTest {

	private static final String USERNAME = "username";
	private static final String EMAIL = "test@example.com";
	private static final String PASSWORD = "password1234";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@SneakyThrows
	@WithMockUser
	@Test
	public void retrievesUserWithAuthenticatedUser() {
		User user = saveTestUser();

		performGetUser(user.getId())//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.username").isString())//
				.andExpect(jsonPath("$.username").value(USERNAME));
	}

	@SneakyThrows
	@Test
	public void doesNotRetrieveUserWithoutAuthenticatedUser() {
		User user = saveTestUser();

		performGetUser(user.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser
	@Test
	public void doesNotRetrieveUserOnUserNotFound() {
		performGetUser(Long.MAX_VALUE)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	private ResultActions performGetUser(Long id) {
		return mockMvc.perform(get("/api/users")//
				.param("userId", id.toString()));
	}

	private User saveTestUser() {
		User user = new User();
		user.getCredentials().setUsername(USERNAME);
		user.getCredentials().setEmail(EMAIL);
		user.getCredentials().setPasswordHash(PASSWORD);
		user.setRole(UserRole.USER);
		return userRepository.saveAndFlush(user);
	}

}
