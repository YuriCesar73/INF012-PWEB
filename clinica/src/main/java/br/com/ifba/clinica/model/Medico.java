package br.com.ifba.clinica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Entity
public class Medico extends Pessoa {

	private String crm;
	
	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;
	
	public Medico(Long id, String nome, String email, String telefone, String endereço, String crm,
			Especialidade especialidade) {
		super(id, nome, email, telefone, endereço);
		this.crm = crm;
		this.especialidade = especialidade;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
}
