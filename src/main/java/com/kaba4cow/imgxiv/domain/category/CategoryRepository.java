package com.kaba4cow.imgxiv.domain.category;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

	boolean existsByName(String name);

}
