package com.br.paciente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.paciente.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	
	public List<Paciente> findByOrderByDadosNomeAsc();
	 
	public List<Paciente> findByActiveTrueOrderByDadosNomeAsc(PageRequest page);

	public Optional<Paciente> findByActiveTrueAndId(Long id); 
	
	public Optional<Paciente> findByCpf(String cpf);

}
