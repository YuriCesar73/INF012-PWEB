package com.br.paciente.exception;

@SuppressWarnings("serial")
public class PacienteNotFound extends RuntimeException {
	
	public PacienteNotFound(String cpf) {
		super("Paciente não existe. CPF: " + cpf);
	}

}