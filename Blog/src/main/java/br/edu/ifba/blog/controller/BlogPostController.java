package br.edu.ifba.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.edu.ifba.blog.models.Post;
import br.edu.ifba.blog.repositories.PostRepository;


@RestController
@RequestMapping("/posts")
public class BlogPostController {

	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping
	public List<Post> listarPosts() {
		
	  
		return postRepository.findAll();
	}
	
	
	
}
