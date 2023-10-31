package com.br.consulta.exception;

import java.time.LocalDate;

@SuppressWarnings("serial")
public class JaPossuiAgendamento extends RuntimeException {

	public JaPossuiAgendamento(LocalDate data) {
		super("Não é permitido a marcação de mais de uma consulta no mesmo dia. Data: " + data);
	}
}