package com.kaba4cow.imgxiv.mail;

import java.util.Map;

public interface MailService {

	void sendMessage(String to, String subject, String template, Map<String, Object> variables);

	default void sendMessage(String to, String subject, String template) {
		sendMessage(to, subject, template, Map.of());
	}

}
