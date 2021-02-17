package com.example.demo.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:mail/emailconfig.properties")
public class SpringMailConfig {

	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private Integer port;
	
	@Value("${spring.mail.username}")
	private String userName;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Value("${spring.mail.protocol}")
	private String protocol;
	
	@Value("${spring.mail.smtp.auth}")
	private String auth;
	
	@Value("${spring.mail.smtp.starttls.enable}")
	private String starttlsEnable;
	
	@Bean
	public JavaMailSender mailSender() throws IOException {
		
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(this.host);
		mailSender.setPort(this.port);
		mailSender.setUsername(this.userName);
		mailSender.setPassword(this.password);
		mailSender.setProtocol(this.protocol);
		
		// JavaMail-specific mail sender configuration, based on javamail.properties
		final Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", this.auth);
		javaMailProperties.setProperty("mail.smtp.starttls.enable", this.starttlsEnable);
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}
}
