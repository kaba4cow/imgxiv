package com.kaba4cow.imgxiv.domain.post;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;

import lombok.SneakyThrows;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class PostControllerTest {

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

	@SneakyThrows
	@Test
	public void createsPostWithAuthenticatedUser() {
		Tag tag = saveTestTag("tag", saveTestCategory());
		User author = authenticateUser(saveTestUser());

		performCreatePost(Set.of(tag))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.authorId").isNumber())//
				.andExpect(jsonPath("$.authorId").value(author.getId()))//
				.andExpect(jsonPath("$.tagIds").isArray())//
				.andExpect(jsonPath("$.tagIds").value(contains(tag.getId().intValue())));
	}

	@SneakyThrows
	@Test
	public void doesNotCreatePostWithoutAuthenticatedUser() {
		Tag tag = saveTestTag("tag", saveTestCategory());

		performCreatePost(Set.of(tag))//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser
	@Test
	public void doesNotCreatePostOnTagNotFound() {
		Tag tag = new Tag();
		tag.setId(Long.MAX_VALUE);
		performCreatePost(Set.of(tag))//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	private ResultActions performCreatePost(Set<Tag> tags) {
		return mockMvc.perform(post("/api/posts")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"tagIds": %s
							}
						""".formatted(//
						tags.stream().map(Tag::getId).toList()//
				)));
	}

	@SneakyThrows
	@Test
	public void editsPostWithAuthenticatedUserAsAuthor() {
		Category category = saveTestCategory();
		Tag tag1 = saveTestTag("tag1", category);
		Tag tag2 = saveTestTag("tag2", category);
		User author = saveTestUser();
		Post post = saveTestPost(author, Set.of(tag1));
		authenticateUser(author);

		performEditPost(post.getId(), Set.of(tag2))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.authorId").isNumber())//
				.andExpect(jsonPath("$.authorId").value(author.getId()))//
				.andExpect(jsonPath("$.tagIds").isArray())//
				.andExpect(jsonPath("$.tagIds").value(contains(tag2.getId().intValue())));
	}

	@SneakyThrows
	@Test
	public void editsPostWithAuthenticatedUserAsModerator() {
		Category category = saveTestCategory();
		Tag tag1 = saveTestTag("tag1", category);
		Tag tag2 = saveTestTag("tag2", category);
		User author = saveTestUser();
		Post post = saveTestPost(author, Set.of(tag1));
		authenticateUser(saveTestUser("moderator", "mod@example.com", UserRole.MODERATOR));

		performEditPost(post.getId(), Set.of(tag2))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.authorId").isNumber())//
				.andExpect(jsonPath("$.authorId").value(author.getId()))//
				.andExpect(jsonPath("$.tagIds").isArray())//
				.andExpect(jsonPath("$.tagIds").value(contains(tag2.getId().intValue())));
	}

	@SneakyThrows
	@Test
	public void doesNotEditPostWithoutAuthenticatedUser() {
		Category category = saveTestCategory();
		Set<Tag> tags = Set.of(saveTestTag("tag2", category));
		User author = saveTestUser();
		Post post = saveTestPost(author, tags);

		performEditPost(post.getId(), tags)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void doesNotEditPostWithAuthenticatedUserAsNonAuthorNorModerator() {
		Set<Tag> tags = Set.of(saveTestTag("tag", saveTestCategory()));
		User author = saveTestUser();
		Post post = saveTestPost(author, tags);
		authenticateUser(saveTestUser("non-author", "na@example.com", UserRole.USER));

		performEditPost(post.getId(), tags)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser
	@Test
	public void doesNotEditPostOnPostNotFound() {
		Set<Tag> tags = Set.of(saveTestTag("tag", saveTestCategory()));
		performEditPost(Long.MAX_VALUE, tags)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	private ResultActions performEditPost(Long id, Set<Tag> tags) {
		return mockMvc.perform(patch("/api/posts")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"id": %s,
								"tagIds": %s
							}
						""".formatted(//
						id, //
						tags.stream().map(Tag::getId).toList()//
				)));
	}

	@SneakyThrows
	@Test
	public void deletesPostWithAuthenticatedUserAsAuthor() {
		User author = authenticateUser(saveTestUser());
		Set<Tag> tags = Set.of(saveTestTag("tag2", saveTestCategory()));
		Post post = saveTestPost(author, tags);

		performDeletePost(post.getId())//
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	public void deletesPostWithAuthenticatedUserAsModerator() {
		User author = saveTestUser();
		Set<Tag> tags = Set.of(saveTestTag("tag2", saveTestCategory()));
		Post post = saveTestPost(author, tags);
		authenticateUser(saveTestUser("moderator", "mod@example.com", UserRole.MODERATOR));

		performDeletePost(post.getId())//
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	public void doesNotDeletePostWithoutAuthenticatedUser() {
		User author = saveTestUser();
		Set<Tag> tags = Set.of(saveTestTag("tag2", saveTestCategory()));
		Post post = saveTestPost(author, tags);

		performDeletePost(post.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void doesNotDeletePostWithAuthenticatedUserAsNonAuthorNorModerator() {
		User author = saveTestUser();
		Set<Tag> tags = Set.of(saveTestTag("tag2", saveTestCategory()));
		Post post = saveTestPost(author, tags);
		authenticateUser(saveTestUser("non-author", "na@example.com", UserRole.USER));

		performDeletePost(post.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser
	@Test
	public void doesNotDeletePostOnPostNotFound() {
		authenticateUser(saveTestUser());

		performDeletePost(Long.MAX_VALUE)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	private ResultActions performDeletePost(Long id) {
		return mockMvc.perform(delete("/api/posts")//
				.param("id", id.toString()));
	}

	@Test
	public void searchesPosts() {
		Category category = saveTestCategory();
		User author = saveTestUser();

		Tag a = saveTestTag("a", category);
		Tag b = saveTestTag("b", category);
		Tag c = saveTestTag("c", category);
		Tag d = saveTestTag("d", category);
		saveTestTag("e", category);

		saveTestPost(author, Set.of(a));
		saveTestPost(author, Set.of(a, b));
		saveTestPost(author, Set.of(a, c));
		saveTestPost(author, Set.of(a, c, d));
		saveTestPost(author, Set.of(c, d));

		expectSearchPostsHasSize(4, "a");
		expectSearchPostsHasSize(1, "b");
		expectSearchPostsHasSize(3, "c");
		expectSearchPostsHasSize(2, "d");
		expectSearchPostsHasSize(0, "e");

		expectSearchPostsHasSize(1, "!a");
		expectSearchPostsHasSize(4, "!b");
		expectSearchPostsHasSize(2, "!c");
		expectSearchPostsHasSize(3, "!d");
		expectSearchPostsHasSize(5, "!e");

		expectSearchPostsHasSize(2, "a c");
		expectSearchPostsHasSize(1, "a d");
		expectSearchPostsHasSize(1, "!a d");
		expectSearchPostsHasSize(0, "a e");
		expectSearchPostsHasSize(0, "b c");
		expectSearchPostsHasSize(2, "c d");

		expectSearchPostsHasSize(2, "a !c");
		expectSearchPostsHasSize(0, "!a !d");
		expectSearchPostsHasSize(1, "a b !c");
		expectSearchPostsHasSize(1, "a c d");
		expectSearchPostsHasSize(1, "a c !d");
		expectSearchPostsHasSize(2, "a !c !d");

		expectSearchPostsHasSize(0, "a b c d");
		expectSearchPostsHasSize(1, "a !b !c !d");
		expectSearchPostsHasSize(0, "!a !b !c !d");
	}

	@SneakyThrows
	private void expectSearchPostsHasSize(int expectedSize, String query) {
		performSearchPosts(query)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$", hasSize(expectedSize)));
	}

	@SneakyThrows
	private ResultActions performSearchPosts(String query) {
		return mockMvc.perform(post("/api/posts/search")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"query": "%s",
								"pageSize": 100
							}
						""".formatted(//
						query//
				)));
	}

	private User authenticateUser(User user) {
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return user;
	}

	private User saveTestUser(String username, String email, UserRole role) {
		User user = new User();
		user.getCredentials().setEmail(email);
		user.getCredentials().setUsername(username);
		user.getCredentials().setPasswordHash("password-hash");
		user.setRole(role);
		return userRepository.saveAndFlush(user);
	}

	private User saveTestUser() {
		return saveTestUser("testuser", "test@example.com", UserRole.USER);
	}

	private Post saveTestPost(User author, Set<Tag> tags) {
		Post post = new Post();
		post.setAuthor(author);
		tags.forEach(post::addTag);
		return postRepository.saveAndFlush(post);
	}

	private Tag saveTestTag(String name, Category category) {
		Tag tag = new Tag();
		tag.getNameAndDescription().setName(name);
		tag.getNameAndDescription().setDescription("description");
		tag.setCategory(category);
		return tagRepository.saveAndFlush(tag);
	}

	private Category saveTestCategory() {
		Category category = new Category();
		category.getNameAndDescription().setName("name");
		category.getNameAndDescription().setDescription("description");
		return categoryRepository.saveAndFlush(category);
	}

}
