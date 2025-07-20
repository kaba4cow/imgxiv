package com.kaba4cow.imgxiv.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.nameAndDescription.name = :name")
	boolean existsByName(@Param("name") String name);

	default Category findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException(String.format("Category not found: %s", id)));
	}

}
