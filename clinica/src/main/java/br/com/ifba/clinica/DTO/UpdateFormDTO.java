package br.com.ifba.clinica.DTO;

import br.com.ifba.clinica.model.Endereco;

public record UpdateFormDTO(String nome, String telefone, Endereco endereco) {

	
}
