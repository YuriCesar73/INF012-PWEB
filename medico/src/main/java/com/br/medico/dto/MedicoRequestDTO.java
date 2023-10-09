package com.br.medico.dto;

import com.br.medico.model.DadosPessoais;
import com.br.medico.model.Especialidade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record MedicoRequestDTO(@Valid DadosPessoais dadosPessoais, @Valid EnderecoRequestDTO endereco, @NotBlank(message = "{crm.not.blank}") String crm, Especialidade especialidade) {

}
