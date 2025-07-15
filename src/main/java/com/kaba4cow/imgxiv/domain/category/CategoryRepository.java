package com.kaba4cow.imgxiv.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.nameAndDescription.name = :name")
	boolean existsByName(@Param("name") String name);

}
