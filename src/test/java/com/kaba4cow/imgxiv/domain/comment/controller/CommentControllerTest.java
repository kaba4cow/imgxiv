package com.kaba4cow.imgxiv.domain.comment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.category.model.Category;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;
import com.kaba4cow.imgxiv.domain.comment.model.Comment;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.post.model.PostImage;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.tag.model.Tag;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.model.User;
import com.kaba4cow.imgxiv.domain.user.model.UserRole;

import lombok.SneakyThrows;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class CommentControllerTest {

	private static final String OLD_COMMENT_TEXT = "Hello World";
	private static final String NEW_COMMENT_TEXT = "Hello Sailor!";

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

	@Autowired
	private ObjectMapper objectMapper;

	@SneakyThrows
	@Test
	public void editsCommentWithAuthenticatedUserAsAuthor() {
		User author = saveTestUser();
		Post post = saveTestPost(author);
		Comment comment = saveTestComment(post, author, OLD_COMMENT_TEXT);
		authenticateUser(author);

		performEditComment(comment.getId(), NEW_COMMENT_TEXT)//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.text").isString())//
				.andExpect(jsonPath("$.text").value(NEW_COMMENT_TEXT));
	}

	@SneakyThrows
	@Test
	public void doesNotEditCommentWithoutAuthenticatedUser() {
		User author = saveTestUser();
		Post post = saveTestPost(author);
		Comment comment = saveTestComment(post, author, OLD_COMMENT_TEXT);

		performEditComment(comment.getId(), NEW_COMMENT_TEXT)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void doesNotEditCommentWithAuthenticatedUserAsNonAuthor() {
		User author = saveTestUser();
		Post post = saveTestPost(author);
		Comment comment = saveTestComment(post, author, OLD_COMMENT_TEXT);
		authenticateUser(saveTestUser("non-author", "na@example.com", UserRole.USER));

		performEditComment(comment.getId(), NEW_COMMENT_TEXT)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser
	@Test
	public void doesNotEditCommentOnCommentNotFound() {
		performEditComment(Long.MAX_VALUE, NEW_COMMENT_TEXT)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	private ResultActions performEditComment(Long id, String text) {
		return mockMvc.perform(patch("/api/comments/{id}", id)//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(objectMapper.writeValueAsString(Map.of("text", text))));
	}

	@SneakyThrows
	@Test
	public void deletesCommentWithAuthenticatedUserAsAuthor() {
		User author = authenticateUser(saveTestUser());
		Post post = saveTestPost(author);
		Comment comment = saveTestComment(post, author, OLD_COMMENT_TEXT);

		performDeleteComment(comment.getId())//
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	public void deletesCommentWithAuthenticatedUserAsModerator() {
		User author = saveTestUser();
		Post post = saveTestPost(author);
		Comment comment = saveTestComment(post, author, OLD_COMMENT_TEXT);
		authenticateUser(saveTestUser("moderator", "mod@example.com", UserRole.MODERATOR));

		performDeleteComment(comment.getId())//
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	public void doesNotDeleteCommentWithoutAuthenticatedUser() {
		User author = saveTestUser();
		Post post = saveTestPost(author);
		Comment comment = saveTestComment(post, author, OLD_COMMENT_TEXT);

		performDeleteComment(comment.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void doesNotDeleteCommentWithAuthenticatedUserAsNonAuthorNorModerator() {
		User author = saveTestUser();
		Post post = saveTestPost(author);
		Comment comment = saveTestComment(post, author, OLD_COMMENT_TEXT);
		authenticateUser(saveTestUser("non-author", "na@example.com", UserRole.USER));

		performDeleteComment(comment.getId())//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@WithMockUser
	@Test
	public void doesNotDeleteCommentOnCommentNotFound() {
		authenticateUser(saveTestUser());

		performDeleteComment(Long.MAX_VALUE)//
				.andExpect(status().is4xxClientError())//
				.andExpect(status().isNotFound());
	}

	@SneakyThrows
	private ResultActions performDeleteComment(Long id) {
		return mockMvc.perform(delete("/api/comments/{id}", id));
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
				.storageKey("storageKey")//
				.fileName("fileName")//
				.fileSize(1L)//
				.contentType("contentType")//
				.thumbnailFileSize(1L)//
				.thumbnailContentType("contentType")//
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
