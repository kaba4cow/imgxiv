package com.kaba4cow.imgxiv.domain.post.specification;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostImage;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;

import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
public class DefaultPostSpecificationTest {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	private User author;

	private Category category;

	@BeforeEach
	void setup() {
		author = createUser();
		category = createCategory();
	}

	@Test
	void matchByRequiredTags() {
		Post ab = createPost("a", "b");
		Post c = createPost("c");

		List<Post> result = findPosts(//
				Set.of("a"), //
				Set.of()//
		);

		assertTrue(result.contains(ab));
		assertFalse(result.contains(c));
	}

	@Test
	void excludeByExcludedTags() {
		Post a = createPost("a");
		Post b = createPost("b");

		List<Post> result = findPosts(//
				Set.of(), //
				Set.of("b")//
		);

		assertTrue(result.contains(a));
		assertFalse(result.contains(b));
	}

	@Test
	void requiredAndExcludedTags() {
		Post ab = createPost("a", "b");
		Post ac = createPost("a", "c");
		Post d = createPost("d");

		List<Post> result = findPosts(//
				Set.of("a"), //
				Set.of("c")//
		);

		assertTrue(result.contains(ab));
		assertFalse(result.contains(ac));
		assertFalse(result.contains(d));
	}

	@Test
	void emptyTagsReturnsAllPosts() {
		Post a = createPost("a");
		Post b = createPost("b");

		List<Post> result = findPosts(//
				Set.of(), //
				Set.of()//
		);

		assertTrue(result.contains(a));
		assertTrue(result.contains(b));
	}

	@Test
	void noMatchingRequiredTags() {
		createPost("a");
		createPost("b");

		List<Post> result = findPosts(//
				Set.of("c"), //
				Set.of()//
		);

		assertTrue(result.isEmpty());
	}

	@Test
	void postMustHaveAllRequiredTags() {
		Post ab = createPost("a", "b");
		Post a = createPost("a");
		Post b = createPost("b");

		List<Post> result = findPosts(//
				Set.of("a", "b"), //
				Set.of()//
		);

		assertTrue(result.contains(ab));
		assertFalse(result.contains(a));
		assertFalse(result.contains(b));
	}

	private List<Post> findPosts(Set<String> requiredTags, Set<String> excludedTags) {
		return postRepository.findAll(new PostSpecification(new CompiledPostQuery(requiredTags, excludedTags)));
	}

	private Post createPost(String... tags) {
		Post post = createPost();
		for (String tagName : tags)
			post.addTag(getOrCreateTag(tagName));
		return postRepository.saveAndFlush(post);
	}

	private Post createPost() {
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
		return postRepository.saveAndFlush(post);
	}

	private Tag getOrCreateTag(String name) {
		if (tagRepository.existsByName(name))
			return tagRepository.findByName(name).orElseThrow();
		Tag tag = new Tag();
		tag.setCategory(category);
		tag.setName(name);
		tag.setDescription("tag-description");
		return tagRepository.saveAndFlush(tag);
	}

	private Category createCategory() {
		Category category = new Category();
		category.setName("category-name");
		category.setDescription("category-description");
		return categoryRepository.saveAndFlush(category);
	}

	private User createUser() {
		User user = new User();
		user.getCredentials().setUsername("username");
		user.getCredentials().setEmail("email");
		user.getCredentials().setPasswordHash("passwordHash");
		user.setRole(UserRole.USER);
		return userRepository.saveAndFlush(user);
	}

}
