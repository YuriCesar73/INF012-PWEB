package br.com.ifba.clinica.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifba.clinica.DTO.ConsultaRequestDTO;
import br.com.ifba.clinica.exception.DiaInvalidoParaConsulta;
import br.com.ifba.clinica.exception.HorarioInvalido;
import br.com.ifba.clinica.exception.MedicoNotFound;
import br.com.ifba.clinica.exception.PacienteNotFound;
import br.com.ifba.clinica.model.Consulta;
import br.com.ifba.clinica.repository.ConsultaRepository;

@Service
public class ConsultaService {
	
	@Autowired
	ConsultaRepository consultaRepository;
	
	@Autowired
	MedicoService medicoService;
	
	@Autowired
	PacienteService pacienteService;

	public void cadastrar(ConsultaRequestDTO data) throws DiaInvalidoParaConsulta {
		
		validarConsulta(data);
		Consulta consulta = new Consulta(data);
		consultaRepository.save(consulta);
	}
	
	private void validarConsulta(ConsultaRequestDTO data){
		try {
			validaDiaDaSemana(data.data().getDayOfWeek());
			validaHorario(data.horario());
			validaMedico(data.medico());
			validaPaciente(data.paciente());
		} catch (DiaInvalidoParaConsulta e) {
			e.printStackTrace();
		} catch (HorarioInvalido error) {
			error.printStackTrace();
		}
		 catch (MedicoNotFound error) {
			error.printStackTrace();
		}
		 catch (PacienteNotFound error) {
			error.printStackTrace();
		}
		
				
	}
	
	private void validaMedico(Long id) throws MedicoNotFound {
		
		try {
			medicoService.findMedico(id);
		} catch (MedicoNotFound e) {
			throw e;
		}
	}
	
	private void validaPaciente(Long id) throws PacienteNotFound {
		pacienteService.findPaciente(id);
	}
	
	private void validaHorario(LocalTime horario) throws HorarioInvalido {
		
		//Horário de funcionamento das 7 às 19. Consultas tem, exatamente, uma hora.
		int horas = horario.getHour();
		int minutos = horario.getMinute(); 
		
		 Long diferenca = Duration.between(horario, LocalTime.now()).toMinutes();
		
		if(horas < 7 || horas >= 19 || minutos != 0 || diferenca > 30) {
			throw new HorarioInvalido();
		}
		
	}

	private void validaDiaDaSemana(DayOfWeek dayOfWeek) throws DiaInvalidoParaConsulta {
		//Dia de funcionamento: Segunda a Sábado
		if(dayOfWeek.equals(dayOfWeek.SUNDAY)) {
			throw new DiaInvalidoParaConsulta();
		}
		
	}


}
