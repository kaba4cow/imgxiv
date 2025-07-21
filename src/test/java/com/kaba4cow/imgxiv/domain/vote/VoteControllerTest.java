package com.kaba4cow.imgxiv.domain.vote;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;

import lombok.SneakyThrows;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class VoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private VoteRepository voteRepository;

	@SneakyThrows
	@Test
	public void createsVoteWithAuthenticatedUser() {
		User author = authenticateUser(saveTestUser());
		Post post = saveTestPost(author);

		performCreateVote(post.getId(), VoteType.UP)//
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	public void doesNotCreateVoteWithoutAuthenticatedUser() {
		Post post = saveTestPost(saveTestUser());

		performCreateVote(post.getId(), VoteType.UP)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void deletesVoteWithAuthenticatedUser() {
		User author = authenticateUser(saveTestUser());
		Post post = saveTestPost(author);
		saveTestVote(post, author, VoteType.UP);

		performDeleteVote(post.getId())//
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	public void doesNotDeleteVoteWithoutAuthenticatedUser() {
		User author = saveTestUser();
		Post post = saveTestPost(author);
		saveTestVote(post, author, VoteType.UP);

		performDeleteVote(post.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void retrievesVoteSummary() {
		User author = saveTestUser();
		Post post = saveTestPost(author);

		int upVoteCount = 7;
		int downVoteCount = 3;
		int totalVoteCount = upVoteCount + downVoteCount;

		int suffix = 0;
		for (int i = 0; i < upVoteCount; i++)
			saveTestVote(post, generateTestUser(suffix++), VoteType.UP);
		for (int i = 0; i < downVoteCount; i++)
			saveTestVote(post, generateTestUser(suffix++), VoteType.DOWN);

		performGetVoteSummary(post.getId())//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.totalVoteCount").value(totalVoteCount))//
				.andExpect(jsonPath("$.upVoteCount").value(upVoteCount))//
				.andExpect(jsonPath("$.downVoteCount").value(downVoteCount));
	}

	@SneakyThrows
	private ResultActions performCreateVote(Long postId, VoteType type) {
		return mockMvc.perform(post("/api/votes")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"postId": %s,
								"type": "%s"
							}
						""".formatted(//
						postId, type//
				)));
	}

	@SneakyThrows
	private ResultActions performDeleteVote(Long postId) {
		return mockMvc.perform(delete("/api/votes")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"postId": %s
							}
						""".formatted(//
						postId//
				)));
	}

	@SneakyThrows
	private ResultActions performGetVoteSummary(Long postId) {
		return mockMvc.perform(get("/api/votes")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"postId": %s
							}
						""".formatted(//
						postId//
				)));
	}

	private User authenticateUser(User user) {
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return user;
	}

	private User generateTestUser(int suffix) {
		User user = new User();
		user.getCredentials().setEmail("test" + suffix + "@example.com");
		user.getCredentials().setUsername("testuser" + suffix);
		user.getCredentials().setPasswordHash("password-hash");
		user.setRole(UserRole.USER);
		return userRepository.saveAndFlush(user);
	}

	private User saveTestUser() {
		User user = new User();
		user.getCredentials().setEmail("test@example.com");
		user.getCredentials().setUsername("testuser");
		user.getCredentials().setPasswordHash("password-hash");
		user.setRole(UserRole.USER);
		return userRepository.saveAndFlush(user);
	}

	private Vote saveTestVote(Post post, User user, VoteType type) {
		Vote vote = new Vote();
		vote.setId(VoteId.of(post, user));
		vote.setPost(post);
		vote.setUser(user);
		vote.setType(type);
		return voteRepository.saveAndFlush(vote);
	}

	private Post saveTestPost(User author) {
		Post post = new Post();
		post.setAuthor(author);
		post.addTag(saveTestTag());
		return postRepository.saveAndFlush(post);
	}

	private Tag saveTestTag() {
		Tag tag = new Tag();
		tag.getNameAndDescription().setName("name");
		tag.getNameAndDescription().setDescription("description");
		tag.setCategory(saveTestCategory());
		return tagRepository.saveAndFlush(tag);
	}

	private Category saveTestCategory() {
		Category category = new Category();
		category.getNameAndDescription().setName("name");
		category.getNameAndDescription().setDescription("description");
		return categoryRepository.saveAndFlush(category);
	}

}
