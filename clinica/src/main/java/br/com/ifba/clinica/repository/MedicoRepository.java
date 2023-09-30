package br.com.ifba.clinica.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifba.clinica.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{
 
	 public List<Medico> findByOrderByDadosNomeAsc();
	 
	 public List<Medico> findByActiveTrueOrderByDadosNomeAsc(PageRequest page);

	 
} 
