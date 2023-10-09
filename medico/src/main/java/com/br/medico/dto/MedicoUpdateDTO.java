package com.br.medico.dto;

import com.br.medico.model.Especialidade;

import jakarta.validation.Valid;

public record MedicoUpdateDTO(String nome, String telefone, String crm, @Valid EnderecoRequestDTO endereco, String email, Especialidade especialidade) {
	
}