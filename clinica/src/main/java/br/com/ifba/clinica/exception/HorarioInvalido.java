package br.com.ifba.clinica.exception;

public class HorarioInvalido extends RuntimeException {

	public HorarioInvalido() {
		super("O horário de funcionamento é das 07:00 às 19:00");
	}
}
