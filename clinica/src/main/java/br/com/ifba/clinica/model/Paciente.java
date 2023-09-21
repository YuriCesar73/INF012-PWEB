package br.com.ifba.clinica.model;

import jakarta.persistence.Entity;

@Entity(name = "pacientes")
public class Paciente extends Pessoa {	
	private String cpf;

	public Paciente(Long id, String nome, String email, String telefone, String endereço, String cpf) {
		super(id, nome, email, telefone, endereço);
		this.cpf = cpf;
	}
	
	public Paciente() {
		
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
}
