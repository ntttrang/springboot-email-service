package com.example.demo.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * The Class SpringMailConfig.
 */
@Configuration
@PropertySource("classpath:mail/emailconfig.properties")
public class SpringMailConfig {

	/** The host. */
	@Value("${spring.mail.host}")
	private String host;

	/** The port. */
	@Value("${spring.mail.port}")
	private Integer port;

	/** The user name. */
	@Value("${spring.mail.username}")
	private String userName;

	/** The password. */
	@Value("${spring.mail.password}")
	private String password;

	/** The protocol. */
	@Value("${spring.mail.protocol}")
	private String protocol;

	/** The auth. */
	@Value("${spring.mail.smtp.auth}")
	private String auth;

	/** The starttls enable. */
	@Value("${spring.mail.smtp.starttls.enable}")
	private String starttlsEnable;

	/**
	 * Mail sender.
	 *
	 * @return the java mail sender
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Email message source.
	 *
	 * @return the resource bundle message source
	 */
	@Bean
	public ResourceBundleMessageSource emailMessageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("mail/MailMessages");
		return messageSource;
	}

	/**
	 * Html template resolver.
	 *
	 * @return the i template resolver
	 */
	private ITemplateResolver htmlTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(2);
		templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
		templateResolver.setPrefix("/mail/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	/**
	 * Text template resolver.
	 *
	 * @return ITemplateResolver
	 */
	private ITemplateResolver textTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(1));
		templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
		templateResolver.setPrefix("/mail/");
		templateResolver.setSuffix(".txt");
		templateResolver.setTemplateMode(TemplateMode.TEXT);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	/**
	 * String template resolver.
	 *
	 * @return the i template resolver
	 */
	private ITemplateResolver stringTemplateResolver() {
		final StringTemplateResolver templateResolver = new StringTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(3));
		// No resolvable pattern, will simply process as a String template everything
		// not previously matched
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	/**
	 * Email template engine.
	 *
	 * @return the template engine
	 */
	@Bean
	public TemplateEngine emailTemplateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();

		// Resolver for TEXT emails
		templateEngine.addTemplateResolver(textTemplateResolver());
		// Resolver for HTML emails (except the editable one)
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		// Resolver for HTML editable emails (which will be treated as a String)
		templateEngine.addTemplateResolver(stringTemplateResolver());
		// Message source, internationalization specific to emails
		templateEngine.setTemplateEngineMessageSource(emailMessageSource());
		return templateEngine;
	}
}
