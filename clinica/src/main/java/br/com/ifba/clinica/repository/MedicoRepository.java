package br.com.ifba.clinica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ifba.clinica.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{
 
	 public List<Medico> findByOrderByDadosNomeAsc();
	 
	 public List<Medico> findByActiveTrueOrderByDadosNomeAsc(PageRequest page);
	 
	 public Optional<Medico> findByActiveTrueAndId(Long id); 

	 
	 //SELECT column FROM table
	 //ORDER BY RAND()
	 //LIMIT 1
	 //public Optional<Medico> findRandoMedico();
} 
