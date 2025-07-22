package com.kaba4cow.imgxiv.domain.vote.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.Vote;
import com.kaba4cow.imgxiv.domain.vote.VoteId;
import com.kaba4cow.imgxiv.domain.vote.VoteRepository;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultVoteService implements VoteService {

	private final VoteRepository voteRepository;

	private final PostRepository postRepository;

	private final VoteSummaryMapper voteSummaryMapper;

	@Override
	public void createVote(VoteCreateRequest request, User user) {
		Post post = postRepository.findByIdOrThrow(request.getPostId());
		Vote vote = new Vote();
		vote.setId(VoteId.of(post, user));
		vote.setPost(post);
		vote.setUser(user);
		vote.setType(request.getType());
		Vote saved = voteRepository.save(vote);
		log.info("Created new vote: {}", saved);
	}

	@Override
	public void deleteVote(Long postId, User user) {
		VoteId id = VoteId.of(postId, user.getId());
		voteRepository.deleteById(id);
	}

	@Override
	public VoteSummaryDto getVoteSummary(Long postId) {
		Post post = postRepository.findByIdOrThrow(postId);
		return voteSummaryMapper.mapToDto(voteRepository.getVoteSummary(post), post);
	}

}
