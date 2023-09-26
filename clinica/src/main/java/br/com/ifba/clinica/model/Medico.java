package br.com.ifba.clinica.model;

import br.com.ifba.clinica.DTO.MedicoRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Medico {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private DadosPessoais dados;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Endereco endereco;
	private String crm;
	
	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;
	
	public Medico() {
	}
	
	
	public Medico(Long id, DadosPessoais dados, Endereco endereco, String crm, Especialidade especialidade) {
		this.id = id;
		this.dados = dados;
		this.endereco = endereco;
		this.crm = crm;
		this.especialidade = especialidade;
	}
	
	public Medico(MedicoRequestDTO medicoData) {
		this.dados = medicoData.dadosPessoais();
		this.endereco = medicoData.endereco();
		this.crm = medicoData.crm();
		this.especialidade = medicoData.especialidade();
	}
	
	public DadosPessoais getDados() {
		return dados;
	}


	public void setDados(DadosPessoais dados) {
		this.dados = dados;
	}


	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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


	public String getNome() {
		return dados.getNome();
	}

	public void setNome(String nome) {
		this.dados.setNome(nome);
	}

	public String getEmail() {
		return dados.getEmail();
	}

	public void setEmail(String email) {
		this.dados.setEmail(email);
	}

	public String getTelefone() {
		return dados.getTelefone();
	}

	public void setTelefone(String telefone) {
		this.dados.setTelefone(telefone);
	}	

}
