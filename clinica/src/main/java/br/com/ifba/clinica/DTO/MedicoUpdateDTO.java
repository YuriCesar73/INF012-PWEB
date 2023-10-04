package br.com.ifba.clinica.DTO;

import br.com.ifba.clinica.model.Especialidade;
import jakarta.validation.Valid;

public record MedicoUpdateDTO(String nome, String telefone, String crm, @Valid EnderecoRequestDTO endereco, String email, Especialidade especialidade) {
	
}
