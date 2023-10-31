package com.br.consulta.exception;

@SuppressWarnings("serial")
public class MedicoIndisponivel extends RuntimeException {

	public MedicoIndisponivel() {
		super("Médico já possui uma consulta marcada na data e horário inseridos");
	}
}