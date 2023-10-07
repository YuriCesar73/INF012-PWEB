package br.com.ifba.clinica.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifba.clinica.DTO.ConsultaRequestDTO;
import br.com.ifba.clinica.exception.DiaInvalidoParaConsulta;
import br.com.ifba.clinica.exception.HorarioInvalido;
import br.com.ifba.clinica.exception.JaPossuiAgendamento;
import br.com.ifba.clinica.exception.MedicoIndisponivel;
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

	public void cadastrar(ConsultaRequestDTO data) throws Exception {
		
		try {
			validarConsulta(data);
		} catch (DiaInvalidoParaConsulta | HorarioInvalido | MedicoNotFound | PacienteNotFound | MedicoIndisponivel
				| JaPossuiAgendamento e) {
			throw e;
		}
		Consulta consulta = new Consulta(data);
		consultaRepository.save(consulta);
	}
	
	private void validarConsulta(ConsultaRequestDTO data) throws DiaInvalidoParaConsulta, HorarioInvalido, MedicoNotFound, PacienteNotFound, MedicoIndisponivel, JaPossuiAgendamento{
		try {
			validaDiaDaSemana(data.data().getDayOfWeek());
			validaHorario(data.horario());
			validaMedico(data.medico());
			validaPaciente(data.paciente());
			validaUnicaConsultaDoDiaPaciente(data.data(), data.paciente());
			validaDisponibilidadeMedico(data.data(), data.horario(), data.medico());
		} catch (DiaInvalidoParaConsulta e) {
			throw e;
		} catch (HorarioInvalido error) {
			throw error;
		}
		 catch (MedicoNotFound error) {
			throw error;
		}
		 catch (PacienteNotFound error) {
			throw error;
		}
		 catch(JaPossuiAgendamento error) {
			 throw error;
		 }
		 catch(MedicoIndisponivel error) {
			 throw error;
		 }
	}
	
	private void validaDisponibilidadeMedico(LocalDate data, LocalTime horario, Long id) throws MedicoIndisponivel {
		//Verifica se o médico está disponível na data e horario estabelecido
		Optional<Consulta> consulta = consultaRepository.findByIdsDataAndIdsHoraAndIdsMedicoId(data, horario, id);
		
		if(consulta.isPresent()) {
			throw new MedicoIndisponivel();
		}
	}

	private void validaUnicaConsultaDoDiaPaciente(LocalDate data, Long id) throws JaPossuiAgendamento {
		//Verifica se o paciente já tem uma consulta no dia
		Optional<Consulta> consulta = consultaRepository.findByIdsDataAndIdsPacienteId(data, id);
		
		if(consulta.isPresent()) {
			throw new JaPossuiAgendamento();
		}
		
	}

	private void validaMedico(Long id) throws MedicoNotFound {
		//Verifica se o médico está ativo no sistema
		try {
			medicoService.findMedicoAtivo(id);
		} catch (MedicoNotFound e) {
			throw e;
		}
	}
	
	private void validaPaciente(Long id) throws PacienteNotFound {
		//Verifica se o paciente está ativo no sistema
		pacienteService.findPacienteAtivo(id);
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
