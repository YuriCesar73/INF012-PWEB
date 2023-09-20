package br.edu.ifba.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.blog.models.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
