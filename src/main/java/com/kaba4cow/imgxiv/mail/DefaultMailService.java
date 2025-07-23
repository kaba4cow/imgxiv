package com.kaba4cow.imgxiv.mail;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kaba4cow.imgxiv.common.exception.MailSendException;

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
	public void sendMessage(MailSendRequest request) {
		try {
			Context context = createContext(request);
			String body = templateEngine.process(request.getTemplate(), context);
			MimeMessage message = createMessage(request, body);
			mailSender.send(message);
		} catch (Exception exception) {
			throw new MailSendException("Could not send mail message", exception);
		}
	}

	private Context createContext(MailSendRequest request) {
		Context context = new Context();
		context.setVariables(request.getVariables());
		return context;
	}

	private MimeMessage createMessage(MailSendRequest request, String body) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
		helper.setFrom(from);
		helper.setTo(request.getTo());
		helper.setSubject(request.getSubject());
		helper.setText(body, true);
		return message;
	}

}
