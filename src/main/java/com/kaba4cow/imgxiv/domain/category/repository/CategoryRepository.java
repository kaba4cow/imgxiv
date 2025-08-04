package com.kaba4cow.imgxiv.domain.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByName(String name);

	Optional<Category> findByName(String name);

	default Category findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException("Category", id));
	}

}
