package com.example.demo.service;

import java.util.Properties;

import javax.mail.SendFailedException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	public void sendEmail(String subject, String message, String recepientEmail ) throws SendFailedException {
		// Get Java Mail Sender
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    // Note: Go to Google Manage Account => Turn "Allow less secure apps: OFF" to "Allow less secure apps: ON"
	    // TODO: Please input your email and password
	    mailSender.setUsername("xxx@gmail.com");
	    mailSender.setPassword("xxx");
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
		
	    // Prepare the content of email
	    SimpleMailMessage sendingMessage = new SimpleMailMessage();
	    sendingMessage.setTo(recepientEmail);
	    sendingMessage.setSubject(subject);
	    sendingMessage.setText(message);
	    
	    // Send email
	    mailSender.send(sendingMessage);
	}

}
