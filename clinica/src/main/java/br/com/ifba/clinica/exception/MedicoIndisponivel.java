package br.com.ifba.clinica.exception;

public class MedicoIndisponivel extends RuntimeException {

	public MedicoIndisponivel() {
		super("Médico já possui uma consulta marcada na data e horário inseridos");
	}
}
