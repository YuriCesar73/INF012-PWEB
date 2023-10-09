package br.com.ifba.clinica.exception;

public class PacienteNotFound extends RuntimeException {
	
	public PacienteNotFound(Long id) {
		super("Paciente não existe. ID " + id);
	}

}
