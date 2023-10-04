package br.com.ifba.clinica.model;

import br.com.ifba.clinica.DTO.PacienteRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Paciente {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private DadosPessoais dados;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Endereco endereco;
	
	private String cpf;
	
	@Column(nullable = false)
	private Boolean active;
	
	public Paciente() {
		
	}
	
	public Paciente(PacienteRequestDTO paciente) {
		this.active = true;
		this.dados = paciente.dadosPessoais();
		this.cpf = paciente.cpf();
		this.endereco = new Endereco(paciente.endereco());
	}
	
	public Paciente(Long id, DadosPessoais dados, Endereco endereco, String cpf) {
		this.id = id;
		this.dados = dados;
		this.endereco = endereco;
		this.cpf = cpf;
		this.active = true;
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
	
	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}	
	
}
