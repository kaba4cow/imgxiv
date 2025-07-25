package com.kaba4cow.imgxiv.domain.vote.service;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteTypeRequest;

public interface VoteService {

	void createVote(Long postId, VoteTypeRequest request, User user);

	void deleteVote(Long postId, User user);

	VoteSummaryDto getVoteSummary(Long postId);

}
