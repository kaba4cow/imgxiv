package com.kaba4cow.imgxiv.domain.comment;

import static org.hamcrest.Matchers.hasSize;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostImage;
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
public class PostCommentControllerTest {

	private static final String COMMENT_TEXT = "Hello World";

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
	private CommentRepository commentRepository;

	@SneakyThrows
	@Test
	public void createsCommentWithAuthenticatedUser() {
		User author = authenticateUser(saveTestUser());
		Post post = saveTestPost(author);

		performCreateComment(post.getId(), COMMENT_TEXT)//
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	public void doesNotCreateCommentWithoutAuthenticatedUser() {
		Post post = saveTestPost(saveTestUser());

		performCreateComment(post.getId(), COMMENT_TEXT)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser
	@Test
	public void doesNotCreateCommentOnPostNotFound() {
		performCreateComment(Long.MAX_VALUE, COMMENT_TEXT)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	private ResultActions performCreateComment(Long postId, String text) {
		return mockMvc.perform(post("/api/posts/{post}/comments", postId)//
				.contentType(MediaType.APPLICATION_JSON)//
				.content("""
							{
								"text": "%s"
							}
						""".formatted(//
						text//
				)));
	}

	@SneakyThrows
	@Test
	public void retrievesAllCommentsByPost() {
		User author = saveTestUser();
		Post post = saveTestPost(author);

		int commentCount = 8;
		for (int i = 0; i < commentCount; i++)
			saveTestComment(post, author, COMMENT_TEXT);

		performGetCommentsByPost(post.getId())//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$", hasSize(commentCount)));
	}

	@SneakyThrows
	@Test
	public void retrievesNoCommentsByPost() {
		User author = saveTestUser();
		Post post = saveTestPost(author);

		performGetCommentsByPost(post.getId())//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").isArray())//
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@SneakyThrows
	@Test
	public void doesNotRetrieveCommentsByPostOnPostNotFound() {
		performGetCommentsByPost(1l)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	private ResultActions performGetCommentsByPost(Long postId) {
		return mockMvc.perform(get("/api/posts/{post}/comments", postId));
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

	private Comment saveTestComment(Post post, User author, String text) {
		Comment comment = new Comment();
		comment.setPost(post);
		comment.setAuthor(author);
		comment.setText(text);
		return commentRepository.saveAndFlush(comment);
	}

	private Post saveTestPost(User author) {
		Post post = new Post();
		post.setAuthor(author);
		post.setPostImage(PostImage.builder()//
				.fileName("fileName")//
				.fileSize(1L)//
				.contentType("contentType")//
				.storageKey("storageKey")//
				.build());
		post.addTag(saveTestTag());
		return postRepository.saveAndFlush(post);
	}

	private Tag saveTestTag() {
		Tag tag = new Tag();
		tag.setName("name");
		tag.setDescription("description");
		tag.setCategory(saveTestCategory());
		return tagRepository.saveAndFlush(tag);
	}

	private Category saveTestCategory() {
		Category category = new Category();
		category.setName("name");
		category.setDescription("description");
		return categoryRepository.saveAndFlush(category);
	}

}
