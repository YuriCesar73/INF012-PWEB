package com.br.medico.exception;

@SuppressWarnings("serial")
public class MedicoNotFound extends RuntimeException {

	
	public MedicoNotFound(String crm) {
		super("Médico não existe. Crm: " + crm);
	}

}