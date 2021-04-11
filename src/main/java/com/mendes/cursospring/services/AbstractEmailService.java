package com.mendes.cursospring.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.mendes.cursospring.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

//	@Autowired
//	private JavaMailSender javaMailSender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();

		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado. Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());

		return sm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
	}

//	@Override
//	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
//		try {
//			MimeMessage mm = prepareMimeMessageFromPedido(obj);
//			sendHtmlEmail(mm);
//		} catch (MessagingException e) {
//			sendOrderConfirmationEmail(obj);
//		}
//	}
//
//	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
//		MimeMessage mm = javaMailSender.createMimeMessage();
//		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
//		mmh.setTo(obj.getCliente().getEmail());
//		mmh.setFrom(sender);
//		mmh.setSubject("Pedido confirmado. Código: " + obj.getId());
//		mmh.setSentDate(new Date(System.currentTimeMillis()));
//		mmh.setText(htmlFromTemplatePedido(obj), true);
//
//		return mm;
//	}
}
