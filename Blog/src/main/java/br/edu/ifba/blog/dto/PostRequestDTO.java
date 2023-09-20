package br.edu.ifba.blog.dto;

import br.edu.ifba.blog.models.Categoria;

public record PostRequestDTO(String titulo, String texto, Long usuario, Categoria categoria) {
	

}
