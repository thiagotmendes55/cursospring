package com.mendes.cursospring.services;

import org.springframework.mail.SimpleMailMessage;

import com.mendes.cursospring.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	
}
