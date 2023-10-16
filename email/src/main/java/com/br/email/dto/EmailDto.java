package com.br.email.dto;

import com.br.email.model.Email;

public record EmailDto(String mailFrom, String mailTo, String 
		mailSubject, String mailText) {
	
	public EmailDto(Email email) {
		this(email.getMailFrom(),email.getMailTo(),email.getMailSubject(),email.getMailText());
	}

}
