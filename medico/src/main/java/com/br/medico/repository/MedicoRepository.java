package com.br.medico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.medico.model.Medico;


public interface MedicoRepository extends JpaRepository<Medico, Long>{
 
	 public List<Medico> findByOrderByDadosNomeAsc();
	 
	 public List<Medico> findByActiveTrueOrderByDadosNomeAsc(PageRequest page);
	 
	 public Optional<Medico> findByActiveTrueAndId(Long id); 

	 
} 