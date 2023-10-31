package com.br.paciente.exception;

@SuppressWarnings("serial")
public class PacienteNotFound extends RuntimeException {
	
	public PacienteNotFound(Long id) {
		super("Paciente não existe. ID " + id);
	}

}