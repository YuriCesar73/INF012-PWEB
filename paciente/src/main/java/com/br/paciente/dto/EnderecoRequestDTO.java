package com.br.paciente.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDTO(
		@NotBlank(message = "{logradouro.not.blank}")
		String logradouro, 
		Integer numero,
		String complemento, 
		@NotBlank(message = "{bairro.not.blank}")
		String bairro,
		@NotBlank(message = "{cidade.not.blank}")
		String cidade,
		@NotBlank(message = "{uf.not.blank}")
		String uf,
		@NotBlank(message = "{cep.not.blank}")
		String cep) {
}