package com.br.consulta.exception;

public class DataInvalida extends RuntimeException{
	
	public DataInvalida() {
		super("Insira uma data válida");
	}

}
