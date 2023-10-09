package com.br.paciente.exception;

public class ValidationInvalid extends RuntimeException {
	
	public ValidationInvalid() {
		super("Dados inválidos.");
	} 

}