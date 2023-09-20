package br.edu.ifba.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.blog.dto.PostRequestDTO;
import br.edu.ifba.blog.dto.PostResponseDTO;
import br.edu.ifba.blog.models.Post;
import br.edu.ifba.blog.models.Usuario;
import br.edu.ifba.blog.repositories.PostRepository;
import br.edu.ifba.blog.repositories.UsuarioRepository;


@RestController
@RequestMapping("/posts")
public class BlogPostController {

	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public List<PostResponseDTO> listarPosts() {
		//return postRepository.findAll();
		return PostResponseDTO.convert(postRepository.findAll());
		//return lista.stream().map(PostDto::new).collect(Collectors.toList());
	}
	
	@PostMapping
	public void publicar(@RequestBody PostRequestDTO postData) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(postData.usuario());
		
		Usuario user = usuario.get();
		
		Post post = new Post(postData, user);
		postRepository.save(post);
		
		
	}
	
	
	
}
