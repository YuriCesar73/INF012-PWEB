package com.br.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.email.dto.EmailDto;
import com.br.email.service.EmailService;



@RestController
@RequestMapping("/send")
public class EmailController {

	@Autowired
	private EmailService service;
	
	@PostMapping
	public ResponseEntity<EmailDto> sendEmail(@RequestBody EmailDto data){
		System.out.println(data.mailFrom());
		return new ResponseEntity<EmailDto>(service.sendEmail(data),HttpStatus.CREATED);
	}
}