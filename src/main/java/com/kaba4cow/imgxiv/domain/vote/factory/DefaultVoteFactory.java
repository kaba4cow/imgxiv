package com.kaba4cow.imgxiv.domain.vote.factory;

import com.kaba4cow.imgxiv.common.annotation.Factory;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.Vote;
import com.kaba4cow.imgxiv.domain.vote.VoteId;
import com.kaba4cow.imgxiv.domain.vote.VoteRepository;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Factory
public class DefaultVoteFactory implements VoteFactory {

	private final PostRepository postRepository;

	private final VoteRepository voteRepository;

	@Override
	public Vote createVote(VoteCreateRequest request, User user) {
		Post post = postRepository.findByIdOrThrow(request.getPostId());
		Vote vote = new Vote();
		vote.setId(VoteId.of(post, user));
		vote.setPost(post);
		vote.setUser(user);
		vote.setType(request.getType());
		Vote saved = voteRepository.save(vote);
		log.info("Created new vote: {}", vote);
		return saved;
	}

}
