package com.example.demo.entities;

/**
 * The Class MailBox.
 */
public class MailBox {

	/** The subject. */
	private String subject;
	
	/** The message. */
	private String message;
	
	/** The recipient email. */
	private String recipientEmail;
	
	/**
	 * Instantiates a new mail box.
	 */
	public MailBox() {
		super();
	}
	
	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the recipient email.
	 *
	 * @return the recipient email
	 */
	public String getRecipientEmail() {
		return recipientEmail;
	}
	
	/**
	 * Sets the recipient email.
	 *
	 * @param recipientEmail the new recipient email
	 */
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	
	
}
