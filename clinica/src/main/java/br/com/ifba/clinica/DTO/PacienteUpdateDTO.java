package br.com.ifba.clinica.DTO;

public record PacienteUpdateDTO(String nome, String telefone, String email, String cpf, EnderecoRequestDTO endereco) {

}
