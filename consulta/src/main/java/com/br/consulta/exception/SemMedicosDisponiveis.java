package com.br.consulta.exception;

public class SemMedicosDisponiveis extends RuntimeException {

	public SemMedicosDisponiveis() {
		super("Não há medicos disponíveis para a data escolhida");
	}
}