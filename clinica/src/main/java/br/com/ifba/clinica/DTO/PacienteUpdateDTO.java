package br.com.ifba.clinica.DTO;

import br.com.ifba.clinica.model.Endereco;

public record PacienteUpdateDTO(String nome, String telefone, String email, String cpf, EnderecoRequestDTO endereco) {

}
