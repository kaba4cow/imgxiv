package com.kaba4cow.imgxiv.domain.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.vote.model.Vote;
import com.kaba4cow.imgxiv.domain.vote.model.VoteId;
import com.kaba4cow.imgxiv.domain.vote.model.VoteSummary;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {

	@Query("""
			SELECT
				COUNT(*) AS totalVoteCount,
				SUM(CASE WHEN v.type = 'UP' THEN 1 ELSE 0 END) AS upVoteCount,
				SUM(CASE WHEN v.type = 'DOWN' THEN 1 ELSE 0 END) AS downVoteCount
			FROM Vote v WHERE v.post = :post
			""")
	VoteSummary getVoteSummary(@Param("post") Post post);

	@Query("""
			SELECT SUM(CASE
				WHEN v.type = 'UP' THEN 1
				WHEN v.type = 'DOWN' THEN -1
			ELSE 0 END)
			FROM Vote v WHERE v.post = :post
			""")
	long countNetVotes(@Param("post") Post post);

}
