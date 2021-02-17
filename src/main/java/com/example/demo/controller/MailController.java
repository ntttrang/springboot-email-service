package com.example.demo.controller;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.MailBox;
import com.example.demo.service.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class MailController.
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Mail Controller")
public class MailController {

	/** The Constant SC_OK. */
	private static final int SC_OK = 200;
	
	/** The Constant SC_BAD_REQUEST. */
	private static final int SC_BAD_REQUEST = 400;
	
	/** The mail service. */
	@Autowired
	MailService mailService;
	
	/**
	 * Index.
	 *
	 * @return the response entity
	 */
	@GetMapping("")
	@ApiOperation(value="Test Server")
	@ApiResponses(value= {
			@ApiResponse(code=SC_OK, message="OK"),
			@ApiResponse(code=SC_BAD_REQUEST, message="Bad Request")
	})
	public ResponseEntity<String> index(){
		return ResponseEntity.ok("Hello world");
	}

	/**
	 * Send text email.
	 *
	 * @param mailBox the mail box
	 * @return the string
	 */
	@PostMapping("/sendTextEmail")
	@ApiOperation(value="Send a simple text email to another email")
	@ApiResponses(value= {
			@ApiResponse(code=SC_OK, message="OK"),
			@ApiResponse(code=SC_BAD_REQUEST, message="Bad Request")
	})
	public String sendTextEmail(@RequestBody MailBox mailBox) {
		String sendEmailRes = "";
		boolean success = true;
		try {
			mailService.sendTextMessage(mailBox.getSubject(), mailBox.getMessage(), mailBox.getRecipientEmail());
		} catch (SendFailedException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		}
		sendEmailRes = success ? "Success" : "Fail";
		return sendEmailRes;

	}
	
	/**
	 * Send HTML email.
	 *
	 * @param mailBox the mail box
	 * @return the string
	 */
	@PostMapping("/sendHTMLEmail")
	@ApiOperation(value="Send a simple HTML email to another email")
	@ApiResponses(value= {
			@ApiResponse(code=SC_OK, message="OK"),
			@ApiResponse(code=SC_BAD_REQUEST, message="Bad Request")
	})
	public String sendHTMLEmail(@RequestBody MailBox mailBox) {
		String sendEmailRes = "";
		boolean success = true;
		try {
			mailService.sendHTMLMessage(mailBox.getSubject(), mailBox.getMessage(), mailBox.getRecipientEmail());
		} catch (SendFailedException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		}
		sendEmailRes = success ? "Success" : "Fail";
		return sendEmailRes;

	}
}
