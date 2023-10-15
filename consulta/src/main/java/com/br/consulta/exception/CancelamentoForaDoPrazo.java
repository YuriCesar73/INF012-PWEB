package com.br.consulta.exception;

public class CancelamentoForaDoPrazo extends RuntimeException {

	public CancelamentoForaDoPrazo() {
		super("Uma consulta só pode ser cancelada com uma antecedencia mínima de 24 horas.");
	}
}