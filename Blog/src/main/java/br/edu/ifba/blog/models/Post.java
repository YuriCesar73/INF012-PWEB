package br.edu.ifba.blog.models;

import br.edu.ifba.blog.dto.PostRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String texto;
	@ManyToOne
	private Usuario usuario;
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	
	
	public Post() {
		
	}
	
	public Post(Long id, String titulo, String texto, Usuario usuario, Categoria categoria) {
		this.id = id;
		this.titulo = titulo;
		this.texto = texto;
		this.usuario = usuario;
		this.categoria = categoria;
	}
	
	public Post(PostRequestDTO postData, Usuario usuario) {
		this.titulo = postData.titulo();
		this.texto = postData.texto();
		this.categoria = postData.categoria();
		this.usuario = usuario;
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	
	
}
