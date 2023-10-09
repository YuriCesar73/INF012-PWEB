package com.br.medico.exception;

public class MedicoNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MedicoNotFound(Long id) {
		super("Médico não existe. ID " + id);
	}

}