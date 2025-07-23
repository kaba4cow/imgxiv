package com.kaba4cow.imgxiv.mail;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(staticName = "of")
@Getter
@ToString
@Builder
public class MailSendRequest {

	private final String to;

	private final String subject;

	private final String template;

	private final Map<String, Object> variables;

}
