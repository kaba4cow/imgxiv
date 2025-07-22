package com.kaba4cow.imgxiv.domain.tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.category.Category;

public interface TagRepository extends JpaRepository<Tag, Long> {

	@Query("SELECT COUNT(t) > 0 FROM Tag t WHERE t.nameAndDescription.name = :name")
	boolean existsByName(@Param("name") String name);

	@Query("SELECT t FROM Tag t WHERE t.nameAndDescription.name = :name")
	Optional<Tag> findByName(String name);

	List<Tag> findByCategory(Category category);

	default Tag findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException("Tag", id));
	}

	default Set<Tag> findByIdsOrThrow(Collection<? extends Long> ids) {
		return ids.stream()//
				.distinct()//
				.map(this::findByIdOrThrow)//
				.collect(Collectors.toSet());
	}

}
