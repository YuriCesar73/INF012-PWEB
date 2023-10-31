package com.br.medico.exception;

@SuppressWarnings("serial")
public class CrmJaCadastrado extends RuntimeException {

	public CrmJaCadastrado(String crm) {
		super("O Crm: " + crm + " já está cadastrado");
	}
}
