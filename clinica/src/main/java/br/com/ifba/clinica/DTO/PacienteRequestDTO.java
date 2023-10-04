package br.com.ifba.clinica.DTO;

import org.hibernate.validator.constraints.br.CPF;

import br.com.ifba.clinica.model.DadosPessoais;
import br.com.ifba.clinica.model.Endereco;
import jakarta.validation.Valid;

public record PacienteRequestDTO(@Valid DadosPessoais dadosPessoais, @CPF String cpf, @Valid EnderecoRequestDTO endereco) {

}
