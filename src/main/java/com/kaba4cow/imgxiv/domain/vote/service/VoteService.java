package com.kaba4cow.imgxiv.domain.vote.service;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;

public interface VoteService {

	void createVote(VoteCreateRequest request, User user);

	void deleteVote(Long postId, User user);

	VoteSummaryDto getVoteSummary(Long postId);

}
