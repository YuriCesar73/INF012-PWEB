package br.com.ifba.clinica.exception;

import java.time.LocalDate;

public class JaPossuiAgendamento extends RuntimeException {

	public JaPossuiAgendamento(LocalDate data) {
		super("Não é permitido a marcação de mais de uma consulta no mesmo dia. Data: " + data);
	}
}
