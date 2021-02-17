package com.example.demo.service;

import java.util.Properties;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleMessage(String subject, String message, String recepientEmail) throws SendFailedException {
		
		// Prepare the content of email
		SimpleMailMessage sendingMessage = new SimpleMailMessage();
		sendingMessage.setTo(recepientEmail);
		sendingMessage.setSubject(subject);
		sendingMessage.setText(message);

		// Send email
		this.mailSender.send(sendingMessage);
	}

}
