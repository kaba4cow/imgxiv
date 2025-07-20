package com.kaba4cow.imgxiv.domain.vote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.domain.post.Post;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {

	@Query("SELECT v FROM Vote v WHERE v.post = :post AND v.type = :type")
	List<Vote> findByPostAndType(@Param("post") Post post, @Param("type") VoteType type);

}
