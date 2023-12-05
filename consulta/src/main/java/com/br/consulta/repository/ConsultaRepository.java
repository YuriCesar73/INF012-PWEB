package com.br.consulta.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.consulta.model.Consulta;


public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	Optional<Consulta> findByIdsDataAndIdsPaciente(LocalDate data, String cpfPaciente);
	
	Optional<Consulta> findByIdsDataAndIdsHorarioAndIdsMedico(LocalDate data, LocalTime horario, String crmMedico);
	
	Optional<Consulta> findByIdsDataAndIdsHorarioAndIdsMedicoAndIdsPacienteAndDesmarcadaTrue(LocalDate data, LocalTime horario, String crmMedico, String cpfPaciente);
	
	List<Consulta> findByIdsMedico(String crmMedico);
	
	List<Consulta> findByIdsPaciente(String cpfPaciente);

	 
}