package br.com.ifba.clinica.exception;

public class ValidationInvalid extends RuntimeException {
	
	public ValidationInvalid() {
		super("Dados inválidos.");
	} 

}
