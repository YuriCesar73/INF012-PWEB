package com.br.consulta.exception;

public class HorarioInvalido extends RuntimeException {

	public HorarioInvalido() {
		super("O horário de funcionamento é das 07:00 às 19:00");
	}
}