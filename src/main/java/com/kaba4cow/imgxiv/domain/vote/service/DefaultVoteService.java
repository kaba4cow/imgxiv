package com.kaba4cow.imgxiv.domain.vote.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.post.repository.PostRepository;
import com.kaba4cow.imgxiv.domain.user.model.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryMapper;
import com.kaba4cow.imgxiv.domain.vote.model.Vote;
import com.kaba4cow.imgxiv.domain.vote.model.VoteId;
import com.kaba4cow.imgxiv.domain.vote.model.VoteType;
import com.kaba4cow.imgxiv.domain.vote.repository.VoteRepository;

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
	public void createVote(Long postId, VoteType type, User user) {
		Post post = postRepository.findByIdOrThrow(postId);
		Vote saved = voteRepository.save(Vote.builder()//
				.id(VoteId.of(post, user))//
				.post(post)//
				.user(user)//
				.type(type)//
				.build());
		log.info("Created new vote: {}", saved);
	}

	@Override
	public void deleteVote(Long postId, User user) {
		VoteId id = VoteId.of(postId, user.getId());
		voteRepository.deleteById(id);
		log.info("Deleted vote: {}", id);
	}

	@Override
	public VoteSummaryDto getVoteSummary(Long postId) {
		Post post = postRepository.findByIdOrThrow(postId);
		return voteSummaryMapper.mapToDto(voteRepository.getVoteSummary(post), post);
	}

}
