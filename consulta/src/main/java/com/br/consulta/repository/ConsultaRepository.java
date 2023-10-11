package com.br.consulta.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.consulta.model.Consulta;


public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	Optional<Consulta> findByIdsDataAndIdsPaciente(LocalDate data, Long id);
	
	Optional<Consulta> findByIdsDataAndIdsHorarioAndIdsMedico(LocalDate data, LocalTime horario, Long id); 
	 
}