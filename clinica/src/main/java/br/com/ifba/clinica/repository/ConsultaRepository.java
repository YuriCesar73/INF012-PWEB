package br.com.ifba.clinica.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ifba.clinica.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	Optional<Consulta> findByIdsDataAndIdsPacienteId(LocalDate data, Long id);
	
	Optional<Consulta> findByIdsDataAndIdsHoraAndIdsMedicoId(LocalDate data, LocalTime horario, Long id); 
	 
	@Query
	(value = "select Medicos_Disponiveis.ID \n"
			+ "from\n"
			+ "(\n"
			+ "select * \n"
			+ "from MEDICO as m\n"
			+ "left join \n" 
			+ "(select * from \n"
			+ "CONSULTA as c\n"
			+ "where data = :data) as c\n"
			+ "on(m.id = c.MEDICO_ID)\n"
			+ ") as Medicos_Disponiveis\n"
			+ "where(Medicos_Disponiveis.DATA is NULL)\n"
			+ "order by RANDOM()\n"
			+ "LIMIT 1", nativeQuery = true)
	Optional<Long> findRandomMedico(@Param("data") LocalDate data);
}
