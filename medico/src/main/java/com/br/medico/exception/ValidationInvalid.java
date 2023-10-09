package com.br.medico.exception;

public class ValidationInvalid extends RuntimeException {
	
	public ValidationInvalid() {
		super("Dados inválidos.");
	} 

}