package com.br.paciente.exception;

@SuppressWarnings("serial")
public class ValidationInvalid extends RuntimeException {
	
	public ValidationInvalid() {
		super("Dados inválidos.");
	} 

}