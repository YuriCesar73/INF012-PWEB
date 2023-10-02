package br.com.ifba.clinica.DTO;

import br.com.ifba.clinica.model.Endereco;

public record MedicoUpdateDTO(String nome, String telefone, String crm, Endereco endereco) {


}
