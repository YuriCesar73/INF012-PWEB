package com.br.paciente.dto;

public record PacienteUpdateDTO(String nome, String telefone, String email, String cpf, EnderecoRequestDTO endereco) {

}
