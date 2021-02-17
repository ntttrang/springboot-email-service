package com.example.demo.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;

	@Qualifier("emailTemplateEngine")
	@Autowired
	private TemplateEngine htmlTemplateEngine;

	private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/email_simple";

	/**
	 * Send text message.
	 *
	 * @param subject the subject
	 * @param message the message
	 * @param recepientEmail the recepient email
	 * @throws SendFailedException the send failed exception
	 */
	public void sendTextMessage(String subject, String message, String recepientEmail) throws SendFailedException {

		// Prepare the content of email
		SimpleMailMessage sendingMessage = new SimpleMailMessage();
		sendingMessage.setTo(recepientEmail);
		sendingMessage.setSubject(subject);
		sendingMessage.setText(message);

		// Send email
		this.mailSender.send(sendingMessage);
	}

	/**
	 * Send HTML message.
	 *
	 * @param subject the subject
	 * @param message the message
	 * @param recepientEmail the recepient email
	 * @throws MessagingException the messaging exception
	 */
	public void sendHTMLMessage(String subject, String message, String recepientEmail) throws MessagingException {

		// Prepare message using a Spring helper
		MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		MimeMessageHelper sendingMessage = new MimeMessageHelper(mimeMessage, "UTF-8");
		sendingMessage.setTo(recepientEmail);
		sendingMessage.setSubject(subject);
		sendingMessage.setText(message);

		// Create the HTML body using Thymeleaf
		// Prepare the evaluation context
		// TODO: Hard code
		String recipientName = "Trang";
		final Context ctx = new Context();
		ctx.setVariable("name", recipientName);
		ctx.setVariable("subscriptionDate", new Date());
		String htmlContent = this.htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
		sendingMessage.setText(htmlContent, true /* isHTML */);

		// Send email
		this.mailSender.send(mimeMessage);
	}

}
