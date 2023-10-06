package br.com.ifba.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifba.clinica.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	
}
