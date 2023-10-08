package br.com.ifba.clinica.exception;

public class MedicoNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MedicoNotFound(Long id) {
		super("Médico não existe. ID " + id);
	}

}
