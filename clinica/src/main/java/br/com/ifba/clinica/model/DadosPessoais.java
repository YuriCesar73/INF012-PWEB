package br.com.ifba.clinica.model;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class DadosPessoais {
	private String nome;
	private String email;
	private String telefone;
	
	public DadosPessoais() {
		
	}
	
	public DadosPessoais(String nome, String email, String telefone) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, nome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DadosPessoais other = (DadosPessoais) obj;
		return Objects.equals(email, other.email) && Objects.equals(nome, other.nome)
				&& Objects.equals(telefone, other.telefone);
	}
	
	
	
	

}
