package com.br.consulta.exception;

public class ConsultaNaoMarcada extends RuntimeException {
	
	public ConsultaNaoMarcada() {
		super("Não existe consulta marcada nessa data e horário");
	}

}