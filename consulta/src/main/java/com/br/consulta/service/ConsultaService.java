package com.br.consulta.service;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.consulta.clients.EmailClient;
import com.br.consulta.clients.MedicoClient;
import com.br.consulta.clients.PacienteClient;
import com.br.consulta.clients.dto.MedicoResponseDTO;
import com.br.consulta.clients.dto.EmailDto;
import com.br.consulta.clients.dto.MedicoAleatorioDTO;
import com.br.consulta.clients.dto.PacienteResponseDTO;
import com.br.consulta.dto.ConsultaCancelamentoRequestDTO;
import com.br.consulta.dto.ConsultaRequestDTO;
import com.br.consulta.dto.ConsultaResponseDTO;
import com.br.consulta.exception.CancelamentoForaDoPrazo;
import com.br.consulta.exception.ConsultaNaoMarcada;
import com.br.consulta.exception.DiaInvalidoParaConsulta;
import com.br.consulta.exception.HorarioInvalido;
import com.br.consulta.exception.JaPossuiAgendamento;
import com.br.consulta.exception.MedicoIndisponivel;
import com.br.consulta.exception.SemMedicosDisponiveis;
import com.br.consulta.model.Consulta;
import com.br.consulta.repository.ConsultaRepository;

import feign.FeignException.FeignClientException;


@Service
public class ConsultaService {
	
	@Autowired
	ConsultaRepository consultaRepository;
	
	@Autowired
	MedicoClient medicoClient;
	
	@Autowired
	PacienteClient pacienteClient;
	
	@Autowired
	EmailClient emailClient;

	public ConsultaResponseDTO cadastrar(ConsultaRequestDTO data) {
		Long id;
		id = validarConsulta(data);
		
		MedicoResponseDTO medico = medicoClient.encontrarMedicoPorId(id).getBody();
		PacienteResponseDTO paciente = pacienteClient.encontrarPacientePorId(data.paciente()).getBody(); 
				
		Consulta consulta = new Consulta(data, id);
		consultaRepository.save(consulta);
		emailClient.enviarEmail(new EmailDto(paciente));
//		EmailDto email = new EmailDto(paciente);
//		System.out.println("\n\n\n\n\n\nEmail: " + email.mailFrom());
		return new ConsultaResponseDTO(consulta.getData(), consulta.getHorario(), medico.nome());
		
	}

	private Long validarConsulta(ConsultaRequestDTO data) {
		validaDiaDaSemana(data.data().getDayOfWeek());
		validaHorario(data.horario(), data.data());
		validaPaciente(data.paciente());
		Long id = validaMedico(data.medico(), data.data(), data.horario());
		validaUnicaConsultaDoDiaPaciente(data.data(), data.paciente());
		
		return id;
	}
	
	private Long validaMedico(Long id, LocalDate data, LocalTime horario) {
		//Verifica se o médico está ativo no sistema
		Long novoId = null;
		Boolean achouMedico = false;
		if(id != null) {
			MedicoResponseDTO medico =  medicoClient.encontrarMedicoPorId(id).getBody();
			validaDisponibilidadeMedico(data, horario, id);
			return id;
		}
		else {
			List<MedicoAleatorioDTO> medicos = medicoClient.listaTodosMedicos().getBody();
			
			for (MedicoAleatorioDTO med : medicos) {
				try {
					validaDisponibilidadeMedico(data, horario, med.id());
					achouMedico = true;
					novoId = med.id();
					break;
				}
				catch (MedicoIndisponivel e) {
					
				}
				
				
			}
			
			if(!achouMedico) {
				throw new SemMedicosDisponiveis();
			}
		}
				return novoId;		
	}
	
	private void validaPaciente(Long id) {
		//Verifica se o paciente está ativo no sistema
		try {
		ResponseEntity<PacienteResponseDTO> p = pacienteClient.encontrarPacientePorId(id);
		}
		catch (FeignClientException e) {
			
		}
	}
	
	
	private Boolean validaDisponibilidadeMedico(LocalDate data, LocalTime horario, Long id) throws MedicoIndisponivel {
		//Verifica se o médico está disponível na data e horario estabelecido
		
		Optional<Consulta> consulta = consultaRepository.findByIdsDataAndIdsHorarioAndIdsMedico(data, horario, id);
		
		if(consulta.isPresent()) {
			throw new MedicoIndisponivel();
		}
		
		return true;
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

	public void cancelar(ConsultaCancelamentoRequestDTO cancelamento) throws ConsultaNaoMarcada, CancelamentoForaDoPrazo {
		Consulta consulta = consultaRepository.findByIdsDataAndIdsPaciente(cancelamento.data(), cancelamento.paciente())
				            .orElseThrow(() -> new ConsultaNaoMarcada());
		
		
		LocalDate dataATual = LocalDate.now();
		LocalTime horarioAtual = LocalTime.now();
		
		
		Period diferencaEntreDias = Period.between(dataATual, cancelamento.data());
		Duration diferencaEntreHoras = Duration.between(horarioAtual, cancelamento.horario());
		
		int diferencaDias = diferencaEntreDias.getDays();
		Long diferencaHoras = diferencaEntreHoras.toHours();
		
		
		if(diferencaDias < 1) {
			if(diferencaHoras < 24) {
				throw new CancelamentoForaDoPrazo();	
			}
		}
				
		consulta.setMotivo(cancelamento.motivo());
		
		consultaRepository.save(consulta);
		
	}

	
}