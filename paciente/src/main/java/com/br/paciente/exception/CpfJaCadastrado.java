package com.br.paciente.exception;

@SuppressWarnings("serial")
public class CpfJaCadastrado extends RuntimeException {

	public CpfJaCadastrado(String cpf) {
		super("O CPF: " + cpf + " já está cadastrado");
	}
}
