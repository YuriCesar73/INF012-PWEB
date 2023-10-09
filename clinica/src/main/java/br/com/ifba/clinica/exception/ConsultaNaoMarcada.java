package br.com.ifba.clinica.exception;

public class ConsultaNaoMarcada extends RuntimeException {
	
	public ConsultaNaoMarcada() {
		super("Não existe consulta marcada nessa data e horário");
	}

}
