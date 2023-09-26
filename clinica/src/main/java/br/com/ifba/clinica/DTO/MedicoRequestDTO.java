package br.com.ifba.clinica.DTO;

import br.com.ifba.clinica.model.DadosPessoais;
import br.com.ifba.clinica.model.Endereco;
import br.com.ifba.clinica.model.Especialidade;


public record MedicoRequestDTO(DadosPessoais dadosPessoais, Endereco endereco, String crm, Especialidade especialidade) {

	
}
