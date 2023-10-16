package com.br.consulta.clients.dto;

import com.br.consulta.model.EmailConfigurationProperties;

public record EmailDto(String mailFrom, String mailTo, String 
		mailSubject, String mailText) {

	public EmailDto(PacienteResponseDTO paciente) {
		this(EmailConfigurationProperties.mailFrom, paciente.email(), EmailConfigurationProperties.mailSubject, EmailConfigurationProperties.mailText);
	}

}
