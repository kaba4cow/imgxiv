package com.kaba4cow.imgxiv.domain.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.common.repository.ExtendedJpaRepository;
import com.kaba4cow.imgxiv.domain.category.Category;

public interface TagRepository extends ExtendedJpaRepository<Tag, Long> {

	@Query("SELECT COUNT(t) > 0 FROM Tag t WHERE t.nameAndDescription.name = :name")
	boolean existsByName(@Param("name") String name);

	@Query("SELECT t FROM Tag t WHERE t.nameAndDescription.name = :name")
	Optional<Tag> findByName(String name);

	List<Tag> findByCategory(Category category);

}
