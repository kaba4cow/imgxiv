package com.kaba4cow.imgxiv.domain.vote.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.VoteId;
import com.kaba4cow.imgxiv.domain.vote.VoteRepository;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteDeleteRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryMapper;
import com.kaba4cow.imgxiv.domain.vote.factory.VoteFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultVoteService implements VoteService {

	private final VoteRepository voteRepository;

	private final PostRepository postRepository;

	private final VoteFactory voteFactory;

	private final VoteSummaryMapper voteSummaryMapper;

	@Override
	public void createVote(VoteCreateRequest request, User user) {
		voteFactory.createVote(request, user);
	}

	@Override
	public void deleteVote(VoteDeleteRequest request, User user) {
		VoteId id = VoteId.of(request.getPostId(), user.getId());
		voteRepository.deleteById(id);
	}

	@Override
	public VoteSummaryDto getVoteSummary(VoteRequest request) {
		Post post = postRepository.findByIdOrThrow(request.getPostId());
		return voteSummaryMapper.mapToDto(voteRepository.getVoteSummary(post), post);
	}

}
