package br.com.ifba.clinica.exception;

public class ValidationInvalid extends Exception {
	private String error;
	Exception exc;
	
	public ValidationInvalid() {
		this.error = "Erro de validação nos dados";
	} 

	public String getError() {
		return error;
	}
}
