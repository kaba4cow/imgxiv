package com.kaba4cow.imgxiv.domain.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.category.Category;

public interface TagRepository extends JpaRepository<Tag, Long> {

	boolean existsByName(String name);

	Optional<Tag> findByName(String name);

	List<Tag> findByCategory(Category category);

	default Tag findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException("Tag", id));
	}

}
