package br.com.ifba.clinica.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Paciente {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private DadosPessoais dados;
	
	@OneToOne
	private Endereco endereco;
	
	private String cpf;
	
	public Paciente() {
		
	}
	
	
	public Paciente(Long id, DadosPessoais dados, Endereco endereco, String cpf) {
		this.id = id;
		this.dados = dados;
		this.endereco = endereco;
		this.cpf = cpf;
	}

	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
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
