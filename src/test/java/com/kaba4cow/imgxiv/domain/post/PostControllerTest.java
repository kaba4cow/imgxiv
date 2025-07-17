package com.kaba4cow.imgxiv.domain.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
	private UserAuthorityRegistry userAuthorityRegistry;

	@BeforeEach
	public void beforeEach() {
		tagRepository.deleteAll();
		categoryRepository.deleteAll();
	}

	@Test
	public void createPost() throws Exception {
		Category category = createTestCategory();
		Tag tag = createTestTag(category);
		User author = createTestUser();
		authenticateTestUser(author);
		mockMvc.perform(post("/api/posts")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"tagIds": [%s]
							}
						"""//
						.formatted(tag.getId())))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.id").isNumber())//
				.andExpect(jsonPath("$.authorId").isNumber())//
				.andExpect(jsonPath("$.authorId").value(author.getId()))//
				.andExpect(jsonPath("$.tagIds").isArray())//
				.andExpect(jsonPath("$.tagIds").value(Matchers.contains(tag.getId().intValue())));
	}

	@Test
	public void getTagsByCategory() throws Exception {
		Category category = createTestCategory();
		createTestTag(category);
		mockMvc.perform(get("/api/tags")//
				.param("categoryId", category.getId().toString()))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$").isNotEmpty());
	}

	private Category createTestCategory() {
		Category category = new Category();
		category.getNameAndDescription().setName("category-name");
		category.getNameAndDescription().setDescription("category-description");
		return categoryRepository.save(category);
	}

	private Tag createTestTag(Category category) {
		Tag tag = new Tag();
		tag.getNameAndDescription().setName("tag-name");
		tag.getNameAndDescription().setDescription("tag-description");
		tag.setCategory(category);
		return tagRepository.save(tag);
	}

	private User createTestUser() {
		User user = new User();
		user.setEmail("test@example.com");
		user.setUsername("testuser");
		user.setPasswordHash("password-hash");
		user.setRole(UserRole.USER);
		return userRepository.save(user);
	}

	private void authenticateTestUser(User user) {
		UserDetails userDetails = UserDetailsAdapter.of(user, userAuthorityRegistry);
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}
