package com.br.email.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.br.email.dto.EmailDto;
import com.br.email.model.Email;
import com.br.email.model.EmailStatus;
import com.br.email.repository.EmailRepository;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private EmailRepository repository;
	
	public EmailDto sendEmail(EmailDto dto) {
		Email email= new Email(dto);
		email.setSendDateEmail(LocalDateTime.now());
		email.setStatus(EmailStatus.SENT);
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom(dto.mailFrom());
		message.setTo(dto.mailTo());
		message.setSubject(dto.mailSubject());
		message.setText(dto.mailText());
		
		emailSender.send(message);
		repository.save(email);
		return dto;
	}


}
