package com.payhub.infra.services;

import com.payhub.infra.dtos.global.EmailDto;
import com.payhub.infra.errors.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	private final Logger logger = LoggerFactory.getLogger(MailService.class);

	public void send(EmailDto data) {
		try {
			var message = new SimpleMailMessage();
			message.setFrom("no-reply@payhub.com");
			message.setTo(data.to());
			message.setSubject("Pay Hub Support - " + data.subject());
			message.setText(data.content());
			mailSender.send(message);

			logger.info("The email sent successfully.");
		} catch (Exception e) {
			logger.info("Failed to send email.");
			throw new BadRequestException("Failed to send email.");
		}
	}
}
