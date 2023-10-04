package br.com.ifba.clinica.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifba.clinica.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	
	public List<Paciente> findByOrderByDadosNomeAsc();
	 
	public List<Paciente> findByActiveTrueOrderByDadosNomeAsc(PageRequest page);

}
