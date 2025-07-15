package com.kaba4cow.imgxiv.domain.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kaba4cow.imgxiv.common.exception.NameConflictException;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryMapper;
import com.kaba4cow.imgxiv.domain.category.service.DefaultCategoryService;

@ExtendWith(MockitoExtension.class)
public class DefaultCategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryMapper categoryMapper;

	@InjectMocks
	private DefaultCategoryService categoryService;

	@Test
	void throwIfNameTaken() {
		CategoryCreateRequest request = new CategoryCreateRequest("category-a", "category-description");

		when(categoryRepository.existsByName("category-a"))//
				.thenReturn(true);

		assertThrows(NameConflictException.class, () -> categoryService.create(request));
	}

	@Test
	void createNewCategory() {
		CategoryCreateRequest request = new CategoryCreateRequest("category-b", "category-description");

		when(categoryRepository.existsByName("category-b"))//
				.thenReturn(false);
		when(categoryRepository.save(Mockito.any()))//
				.thenAnswer(i -> i.getArgument(0));

		when(categoryMapper.mapToDto(Mockito.any()))//
				.thenReturn(new CategoryDto(0L, "category-b", "category-description"));

		CategoryDto result = categoryService.create(request);

		assertEquals("category-b", result.getName());
		assertEquals("category-description", result.getDescription());
	}

}
