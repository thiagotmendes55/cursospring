package com.mendes.cursospring.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {
	private static final Logger log = LoggerFactory.getLogger(MockEmailService.class);
	
	public void sendEmail(SimpleMailMessage msg) {
		log.info("Simulando envio de email...");
		log.info(msg.toString());
		log.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		log.info("Simulando envio de email html...");
		log.info(msg.toString());
		log.info("Email enviado");
	};
}
