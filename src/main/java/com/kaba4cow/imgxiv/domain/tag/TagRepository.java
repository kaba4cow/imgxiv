package com.kaba4cow.imgxiv.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

	@Query("SELECT COUNT(t) > 0 FROM Tag t WHERE t.nameAndDescription.name = :name")
	boolean existsByName(@Param("name") String name);

}
