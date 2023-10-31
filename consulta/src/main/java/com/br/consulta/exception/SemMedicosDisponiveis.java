package com.br.consulta.exception;

@SuppressWarnings("serial")
public class SemMedicosDisponiveis extends RuntimeException {

	public SemMedicosDisponiveis() {
		super("Não há medicos disponíveis para a data escolhida");
	}
}