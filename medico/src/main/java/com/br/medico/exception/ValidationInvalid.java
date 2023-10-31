package com.br.medico.exception;

@SuppressWarnings("serial")
public class ValidationInvalid extends RuntimeException {
	
	public ValidationInvalid() {
		super("Dados inválidos.");
	} 

}