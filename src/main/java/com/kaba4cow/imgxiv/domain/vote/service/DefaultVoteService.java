package com.kaba4cow.imgxiv.domain.vote.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.Vote;
import com.kaba4cow.imgxiv.domain.vote.VoteId;
import com.kaba4cow.imgxiv.domain.vote.VoteRepository;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteDeleteRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultVoteService implements VoteService {

	private final VoteRepository voteRepository;

	private final PostRepository postRepository;

	private final VoteSummaryMapper voteSummaryMapper;

	@Override
	public void createVote(VoteCreateRequest request, User user) {
		Post post = getPost(request.getPostId());
		Vote vote = new Vote();
		vote.setId(VoteId.of(post, user));
		vote.setPost(post);
		vote.setUser(user);
		vote.setType(request.getType());
		voteRepository.save(vote);
	}

	private Post getPost(Long postId) {
		return postRepository.findById(postId)//
				.orElseThrow(() -> new NotFoundException("Post not found"));
	}

	@Override
	public void deleteVote(VoteDeleteRequest request, User user) {
		VoteId id = VoteId.of(request.getPostId(), user.getId());
		voteRepository.deleteById(id);
	}

	@Override
	public VoteSummaryDto getVoteSummary(Post post) {
		return voteSummaryMapper.mapToDto(voteRepository.getVoteSummary(post), post);
	}

}
