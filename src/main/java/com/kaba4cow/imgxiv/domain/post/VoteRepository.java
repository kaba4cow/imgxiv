package com.kaba4cow.imgxiv.domain.post;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, UUID> {

}
