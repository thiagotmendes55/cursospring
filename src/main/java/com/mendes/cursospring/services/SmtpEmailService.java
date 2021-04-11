package com.mendes.cursospring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {
	private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	MailSender mailSender;
	
	public void sendEmail(SimpleMailMessage msg) {
		log.info("Enviando email...");
		mailSender.send(msg);
		log.info("Email enviado");
	};
}
