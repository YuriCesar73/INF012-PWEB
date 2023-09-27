package br.com.ifba.clinica.DTO;

public record EnderecoRequestDTO(String logradouro, Integer numero, String complemento, String bairro, String cidade, String uf,
		String cep) {
	
	

}
