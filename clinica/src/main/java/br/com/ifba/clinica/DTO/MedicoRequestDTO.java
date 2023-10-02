package br.com.ifba.clinica.DTO;

import br.com.ifba.clinica.model.DadosPessoais;
import br.com.ifba.clinica.model.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


public record MedicoRequestDTO(@Valid DadosPessoais dadosPessoais, @Valid EnderecoRequestDTO endereco, @NotBlank(message = "{crm.not.blank}") String crm, Especialidade especialidade) {

	
}
