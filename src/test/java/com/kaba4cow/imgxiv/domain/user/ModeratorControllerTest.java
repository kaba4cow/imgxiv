package com.kaba4cow.imgxiv.domain.user;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class ModeratorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.VIEW_MODERATORS)
	@Test
	public void retrievesModerators() {
		int moderatorCount = 10;
		for (int i = 0; i < moderatorCount; i++)
			saveTestUser("MODERATOR" + i, UserRole.MODERATOR);
		saveTestUser("USER", UserRole.USER);
		saveTestUser("ADMIN", UserRole.ADMIN);

		performGetModerators()//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$", hasSize(moderatorCount)));
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.VIEW_MODERATORS)
	@Test
	public void retrievesModeratorsWithAuthority() {
		performGetModerators()//
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	public void doesNotRetrieveModeratorsWithoutAuthority() {
		performGetModerators()//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	private ResultActions performGetModerators() {
		return mockMvc.perform(get("/api/moderators"));
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.MANAGE_MODERATORS)
	@Test
	public void assignsModeratorWithAuthority() {
		User user = saveTestUser("username", UserRole.USER);

		performAssignModerator(user.getId())//
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	public void doesNotAssignModeratorWithoutAuthority() {
		User user = saveTestUser("username", UserRole.USER);

		performAssignModerator(user.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void doesNotAssignModeratorOnAdminRole() {
		User user = saveTestUser("username", UserRole.ADMIN);

		performAssignModerator(user.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	private ResultActions performAssignModerator(Long id) {
		return mockMvc.perform(post("/api/moderators/{id}/assign", id));
	}

	@SneakyThrows
	@WithMockUser(authorities = UserAuthorities.MANAGE_MODERATORS)
	@Test
	public void removesModeratorWithAuthority() {
		User user = saveTestUser("username", UserRole.MODERATOR);

		performRemoveModerator(user.getId())//
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	public void doesNotRemoveModeratorWithoutAuthority() {
		User user = saveTestUser("username", UserRole.MODERATOR);

		performRemoveModerator(user.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void doesNotRemoveModeratorOnNonModeratorRole() {
		User user = saveTestUser("username", UserRole.USER);

		performRemoveModerator(user.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	private ResultActions performRemoveModerator(Long id) {
		return mockMvc.perform(post("/api/moderators/{id}/remove", id));
	}

	private User saveTestUser(String username, UserRole role) {
		User user = new User();
		user.getCredentials().setUsername(username);
		user.getCredentials().setEmail(username + "@example.mail");
		user.getCredentials().setPasswordHash("password1234");
		user.setRole(role);
		return userRepository.saveAndFlush(user);
	}

}
