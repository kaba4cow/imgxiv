package com.kaba4cow.imgxiv.setup;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.embeddable.NameAndDescription;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Profile("dev")
@ConditionalOnProperty(prefix = "setup.category-and-tag-initializer", name = "enable", havingValue = "true")
@Component
public class CategoryAndTagInitializer implements ApplicationRunner {

	private final CategoryRepository categoryRepository;

	private final TagRepository tagRepository;

	@Override
	public void run(ApplicationArguments args) {
		for (int i = 1; i <= 10; i++) {
			Category category = categoryRepository.save(createCategory("c%s".formatted(i)));
			for (int j = 1; j <= 10; j++)
				tagRepository.save(createTag("c%st%s".formatted(i, j), category));
		}
	}

	private Category createCategory(String name) {
		return Category.builder()//
				.nameAndDescription(NameAndDescription.builder()//
						.name(name)//
						.description("")//
						.build())//
				.build();
	}

	private Tag createTag(String name, Category category) {
		return Tag.builder()//
				.category(category)//
				.nameAndDescription(NameAndDescription.builder()//
						.name(name)//
						.description("")//
						.build())//
				.build();
	}

}
