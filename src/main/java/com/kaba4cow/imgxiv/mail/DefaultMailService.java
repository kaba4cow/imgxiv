package com.kaba4cow.imgxiv.mail;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultMailService implements MailService {

	private final JavaMailSender mailSender;

	private final TemplateEngine templateEngine;

	@Value("${mail.from}")
	private String from;

	@Override
	public void sendMessage(String to, String subject, String template, Map<String, Object> variables) {
		try {
			Context context = createContext(variables);
			String body = templateEngine.process(template, context);
			MimeMessage message = createMessage(to, subject, body);
			mailSender.send(message);
		} catch (Exception exception) {
			throw new RuntimeException("Could not send mail message", exception);
		}
	}

	private Context createContext(Map<String, Object> variables) {
		Context context = new Context();
		context.setVariables(variables);
		return context;
	}

	private MimeMessage createMessage(String to, String subject, String body) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);
		return message;
	}

}
