package com.br.medico.exception;

@SuppressWarnings("serial")
public class MedicoNotFound extends RuntimeException {

	
	public MedicoNotFound(Long id) {
		super("Médico não existe. ID " + id);
	}

}