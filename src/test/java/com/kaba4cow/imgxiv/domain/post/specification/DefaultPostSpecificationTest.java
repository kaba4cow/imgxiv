package com.kaba4cow.imgxiv.domain.post.specification;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.link.posttag.PostTag;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

	@BeforeAll
	void setup() {
		author = createUser();
		category = createCategory();
	}

	private Post createPost(String... tags) {
		Post post = new Post();
		for (String tagName : tags) {
			Tag tag = createTag(tagName);
			PostTag postTag = new PostTag();
			postTag.setPost(post);
			postTag.setTag(tag);
			post.getPostTags().add(postTag);
		}
		return postRepository.saveAndFlush(post);
	}

	private Tag createTag(String name) {
		Tag tag = new Tag();
		tag.getNameAndDescription().setName(name);
		tag.getNameAndDescription().setDescription("tag-description");
		return tagRepository.saveAndFlush(tag);
	}

	private Category createCategory() {
		Category category = new Category();
		category.getNameAndDescription().setName("category-name");
		category.getNameAndDescription().setDescription("category-description");
		return categoryRepository.saveAndFlush(category);
	}

	private User createUser() {
		User user = new User();
		user.setUsername("username");
		user.setEmail("email");
		user.setPasswordHash("passwordHash");
		user.setRole(UserRole.USER);;
		return userRepository.saveAndFlush(user);
	}

}
