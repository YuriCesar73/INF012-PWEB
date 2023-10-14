package com.br.consulta.service;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.consulta.clients.MedicoClient;
import com.br.consulta.clients.PacienteClient;
import com.br.consulta.clients.dto.MedicoResponseDTO;
import com.br.consulta.clients.dto.PacienteResponseDTO;
import com.br.consulta.dto.ConsultaRequestDTO;
import com.br.consulta.dto.ConsultaResponseDTO;
import com.br.consulta.exception.DiaInvalidoParaConsulta;
import com.br.consulta.exception.HorarioInvalido;
import com.br.consulta.exception.JaPossuiAgendamento;
import com.br.consulta.exception.MedicoIndisponivel;
import com.br.consulta.model.Consulta;
import com.br.consulta.repository.ConsultaRepository;


@Service
public class ConsultaService {
	
	@Autowired
	ConsultaRepository consultaRepository;
	
	@Autowired
	MedicoClient medicoClient;
	
	@Autowired
	PacienteClient pacienteClient;

	public ConsultaResponseDTO cadastrar(ConsultaRequestDTO data) {
		
		validarConsulta(data);
		
		MedicoResponseDTO medico = medicoClient.encontrarMedicoPorId(data.medico()).getBody();
		PacienteResponseDTO paciente = pacienteClient.encontrarPacientePorId(data.paciente()).getBody(); 
				
		Consulta consulta = new Consulta(data);
		consultaRepository.save(consulta);
		return new ConsultaResponseDTO(consulta.getData(), consulta.getHorario(), medico.nome());
		
	}

	private void validarConsulta(ConsultaRequestDTO data) {
		validaDiaDaSemana(data.data().getDayOfWeek());
		validaHorario(data.horario(), data.data());
		validaPaciente(data.paciente());
		validaMedico(data.medico(), data.data(), data.horario());
		validaUnicaConsultaDoDiaPaciente(data.data(), data.paciente());
	}
	
	private Long validaMedico(Long id, LocalDate data, LocalTime horario) {
		//Verifica se o médico está ativo no sistema
		MedicoResponseDTO medico =  medicoClient.encontrarMedicoPorId(id).getBody();
		validaDisponibilidadeMedico(data, horario, id);
		
		return id;		
	}
	
	private void validaPaciente(Long id) {
		//Verifica se o paciente está ativo no sistema
		pacienteClient.encontrarPacientePorId(id);
	}
	
	
	private void validaDisponibilidadeMedico(LocalDate data, LocalTime horario, Long id) throws MedicoIndisponivel {
		//Verifica se o médico está disponível na data e horario estabelecido
		Optional<Consulta> consulta = consultaRepository.findByIdsDataAndIdsHorarioAndIdsMedico(data, horario, id);
		
		if(consulta.isPresent()) {
			throw new MedicoIndisponivel();
		}
	}
	
	private void validaUnicaConsultaDoDiaPaciente(LocalDate data, Long id) throws JaPossuiAgendamento {
		//Verifica se o paciente já tem uma consulta no dia
		Optional<Consulta> consulta = consultaRepository.findByIdsDataAndIdsPaciente(data, id);
		
		if(consulta.isPresent()) {
			throw new JaPossuiAgendamento(data);
		}
		
	}
	
	private void validaHorario(LocalTime horario, LocalDate data) throws HorarioInvalido {
		
		//Horário de funcionamento das 7 às 19. Consultas tem, exatamente, uma hora.
		
		LocalDate dataAtual = LocalDate.now();		
		Period diferencaEntreDias = Period.between(dataAtual, data);
		
		if(diferencaEntreDias.getDays() < 1) {
			
			Duration diferenca = Duration.between(LocalTime.now(), horario);
			if(diferenca.toHours() < 1) {
				if(diferenca.toMinutes() < 30) {
					throw new HorarioInvalido();
				}
			}
		}
		
		int horas = horario.getHour();
		int minutos = horario.getMinute(); 
		
		if(horas < 7 || horas >= 19 || minutos != 0) {
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