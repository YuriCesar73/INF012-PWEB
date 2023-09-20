package br.edu.ifba.blog.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.blog.models.Categoria;
import br.edu.ifba.blog.models.Post;

public record PostResponseDTO(String titulo, String texto, String usuario, Categoria categoria) {

	public PostResponseDTO(Post post) {
		this(post.getTitulo(), post.getTexto(), post.getUsuario().getNome(), post.getCategoria());
	}
	
	public static List<PostResponseDTO> convert(List<Post> lista) {
		return lista.stream().map(PostResponseDTO::new).collect(Collectors.toList());
	}
}
