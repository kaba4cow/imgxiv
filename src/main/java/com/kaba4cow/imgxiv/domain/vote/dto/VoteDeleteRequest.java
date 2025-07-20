package com.kaba4cow.imgxiv.domain.vote.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Request for deleting a vote")
public class VoteDeleteRequest extends VoteRequest {

}
