package com.kaba4cow.imgxiv.domain.tag;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, UUID> {

}
