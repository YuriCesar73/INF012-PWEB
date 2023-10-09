package com.br.paciente.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.br.paciente.model.DadosPessoais;

import jakarta.validation.Valid;

public record PacienteRequestDTO(@Valid DadosPessoais dadosPessoais, @CPF String cpf, @Valid EnderecoRequestDTO endereco) {

}