package com.kaba4cow.imgxiv.domain.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.common.repository.ExtendedJpaRepository;

public interface CategoryRepository extends ExtendedJpaRepository<Category, Long> {

	@Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.nameAndDescription.name = :name")
	boolean existsByName(@Param("name") String name);

}
