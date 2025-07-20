package com.kaba4cow.imgxiv.domain.post;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.auth.userdetails.UserDetailsAdapter;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;
import com.kaba4cow.imgxiv.domain.user.role.UserAuthorityRegistry;

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

	@Autowired
	private UserAuthorityRegistry userAuthorityRegistry;

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

	private User authenticateUser(User user) {
		UserDetails userDetails = UserDetailsAdapter.of(user, userAuthorityRegistry);
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return user;
	}

	private User saveTestUser() {
		User user = new User();
		user.getCredentials().setEmail("test@example.com");
		user.getCredentials().setUsername("testuser");
		user.getCredentials().setPasswordHash("password-hash");
		user.setRole(UserRole.USER);
		return userRepository.saveAndFlush(user);
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
