package br.edu.ifba.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.blog.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
