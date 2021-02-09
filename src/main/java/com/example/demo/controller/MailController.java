package com.example.demo.controller;

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

@RestController
@RequestMapping("/api/v1")
public class MailController {

	@Autowired
	MailService mailService;
	@GetMapping("")
	public ResponseEntity<String> index(){
		return ResponseEntity.ok("Hello world");
	}

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestBody MailBox mailBox) {
		System.out.println("Test email!");
		String sendEmailRes = "";
		boolean success = true;
		try {
			mailService.sendEmail(mailBox.getSubject(), mailBox.getMessage(), mailBox.getRecipientEmail());
		} catch (SendFailedException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		}
		sendEmailRes = success ? "Success" : "Fail";
		return sendEmailRes;

	}
}
