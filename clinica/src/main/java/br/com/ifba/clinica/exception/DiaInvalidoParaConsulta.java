package br.com.ifba.clinica.exception;

public class DiaInvalidoParaConsulta extends RuntimeException {

	public DiaInvalidoParaConsulta() {
		super("A clínica funciona de segunda a sábado");
	
	}
}
