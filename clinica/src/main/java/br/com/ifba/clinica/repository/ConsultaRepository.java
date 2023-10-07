package br.com.ifba.clinica.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifba.clinica.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	Optional<Consulta> findByIdsDataAndIdsPacienteId(LocalDate data, Long id);
	
	Optional<Consulta> findByIdsDataAndIdsHoraAndIdsMedicoId(LocalDate data, LocalTime horario, Long id);
	 
}
