package com.br.consulta.exception;

@SuppressWarnings("serial")
public class DiaInvalidoParaConsulta extends RuntimeException {

	public DiaInvalidoParaConsulta() {
		super("A clínica funciona de segunda a sábado");
	
	}
}