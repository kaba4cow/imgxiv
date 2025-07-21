package com.kaba4cow.imgxiv.domain.vote.factory;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.Vote;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;

public interface VoteFactory {

	Vote createVote(VoteCreateRequest request, User user);

}
