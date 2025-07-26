package com.kaba4cow.imgxiv.domain.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.domain.user.Credentials;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;

import lombok.SneakyThrows;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class ProfileControllerTest {

	private static final String OLD_USERNAME = "old_username";
	private static final String NEW_USERNAME = "new_username";

	private static final String OLD_EMAIL = "old@example.com";
	private static final String NEW_EMAIL = "new@example.com";

	private static final String OLD_PASSWORD = "old_password";
	private static final String NEW_PASSWORD = "new_password";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@SneakyThrows
	@Test
	public void changesUsername() {
		User user = saveAndAuthenticateTestUser();

		performChangeField("username", NEW_USERNAME)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.username").isString())//
				.andExpect(jsonPath("$.username").value(NEW_USERNAME))//
				.andExpect(jsonPath("$.email").isString())//
				.andExpect(jsonPath("$.email").value(OLD_EMAIL));
		ensureUserCredentialsEquals(NEW_USERNAME, Credentials::getUsername, user);
	}

	@SneakyThrows
	@Test
	public void doesNotChangeUsernameIfTaken() {
		saveTestUser(NEW_USERNAME, NEW_EMAIL, NEW_PASSWORD);
		User user = saveAndAuthenticateTestUser();

		performChangeField("username", NEW_USERNAME)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isConflict());
		ensureUserCredentialsEquals(OLD_USERNAME, Credentials::getUsername, user);
	}

	@SneakyThrows
	@Test
	public void changesEmail() {
		User user = saveAndAuthenticateTestUser();

		performChangeField("email", NEW_EMAIL)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.username").isString())//
				.andExpect(jsonPath("$.username").value(OLD_USERNAME))//
				.andExpect(jsonPath("$.email").isString())//
				.andExpect(jsonPath("$.email").value(NEW_EMAIL));
		ensureUserCredentialsEquals(NEW_EMAIL, Credentials::getEmail, user);
	}

	@SneakyThrows
	@Test
	public void doesNotChangeEmailIfTaken() {
		saveTestUser(NEW_USERNAME, NEW_EMAIL, NEW_PASSWORD);
		User user = saveAndAuthenticateTestUser();

		performChangeField("email", NEW_EMAIL)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isConflict());
		ensureUserCredentialsEquals(OLD_EMAIL, Credentials::getEmail, user);
	}

	@SneakyThrows
	@Test
	public void changesPassword() {
		User user = saveAndAuthenticateTestUser();

		performChangePassword(OLD_PASSWORD, NEW_PASSWORD)//
				.andExpect(status().isNoContent());
		ensureUserCredentialsEquals(NEW_PASSWORD, Credentials::getPasswordHash, user);
	}

	@SneakyThrows
	@Test
	public void doesNotChangePasswordIfMatchFails() {
		User user = saveAndAuthenticateTestUser();

		performChangePassword(NEW_PASSWORD, NEW_PASSWORD)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isBadRequest());
		ensureUserCredentialsEquals(OLD_PASSWORD, Credentials::getPasswordHash, user);
	}

	private <T> void ensureUserCredentialsEquals(T expected, Function<Credentials, T> actualSupplier, User user) {
		T actual = userRepository.findById(user.getId())//
				.map(User::getCredentials)//
				.map(actualSupplier::apply)//
				.orElseThrow();
		assertEquals(expected, actual);
	}

	@SneakyThrows
	private ResultActions performChangeField(String fieldName, String fieldValue) {
		return mockMvc.perform(patch("/api/profile/" + fieldName)//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"%s": "%s"
							}
						""".formatted(//
						fieldName, fieldValue//
				)));
	}

	@SneakyThrows
	private ResultActions performChangePassword(String oldPassword, String newPassword) {
		return mockMvc.perform(patch("/api/profile/password")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"oldPassword": "%s",
								"newPassword": "%s"
							}
						""".formatted(//
						oldPassword, //
						newPassword//
				)));
	}

	private User saveAndAuthenticateTestUser() {
		return authenticateUser(saveTestUser(//
				OLD_USERNAME, //
				OLD_EMAIL, //
				OLD_PASSWORD//
		));
	}

	private User authenticateUser(User user) {
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return user;
	}

	private User saveTestUser(String username, String email, String password) {
		User user = new User();
		user.getCredentials().setUsername(username);
		user.getCredentials().setEmail(email);
		user.getCredentials().setPasswordHash(password);
		user.setRole(UserRole.USER);
		return userRepository.saveAndFlush(user);
	}

	@TestConfiguration
	static class TestConfig {

		@SuppressWarnings("deprecation")
		@Primary
		@Bean
		public PasswordEncoder testPasswordEncoder() {
			return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
		}

	}

}
