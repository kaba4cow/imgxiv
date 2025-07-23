package com.kaba4cow.imgxiv.mail;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

	@Autowired
	private MailService mailService;

	@Test
	public void sendMessage_doesNotThrow() {
		assertDoesNotThrow(() -> mailService.sendMessage(MailSendRequest.builder()//
				.to("test@example.com")//
				.subject("Test")//
				.template("mail/test.html")//
				.variables(Map.of("username", "TestUser"))//
				.build()));
	}

}
