package com.br.consulta.service;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.consulta.clients.MedicoClient;
import com.br.consulta.clients.PacienteClient;
import com.br.consulta.clients.dto.EmailDto;
import com.br.consulta.clients.dto.MedicoAleatorioDTO;
import com.br.consulta.clients.dto.MedicoResponseDTO;
import com.br.consulta.clients.dto.PacienteResponseDTO;
import com.br.consulta.dto.ConsultaCancelamentoRequestDTO;
import com.br.consulta.dto.ConsultaRequestDTO;
import com.br.consulta.dto.ConsultaResponseDTO;
import com.br.consulta.exception.CancelamentoForaDoPrazo;
import com.br.consulta.exception.ConsultaNaoMarcada;
import com.br.consulta.exception.DataInvalida;
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
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoClient medicoClient;
	
	@Autowired
	private PacienteClient pacienteClient;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public ConsultaResponseDTO cadastrar(ConsultaRequestDTO data) {
		validaDataHorario(data.data(), data.horario());
	
		PacienteResponseDTO paciente = validaPaciente(data.paciente());
		MedicoResponseDTO medico = validaMedico(data.medico(), data.data(), data.horario());
		Consulta consulta = validaConsulta(data, medico.crm(), paciente.cpf());
		
		consultaRepository.save(consulta);
		rabbitTemplate.convertAndSend("email_enviar.exchange","", new EmailDto(paciente));
		return new ConsultaResponseDTO(consulta.getData(), consulta.getHorario(), medico.nome());
	}

	private void validaDataHorario(LocalDate data, LocalTime horario) {
		validaDiaDaSemana(data.getDayOfWeek());
		validaHorario(horario, data);
	}
	
	private Consulta validaConsulta(ConsultaRequestDTO consulta, String medicoCrm, String pacienteCpf) {
		validaUnicaConsultaDoDiaPaciente(consulta.data(), pacienteCpf);
		
		Optional<Consulta> consultaRegistro  = consultaRepository.findByIdsDataAndIdsHorarioAndIdsMedicoAndIdsPacienteAndDesmarcadaTrue(consulta.data(), consulta.horario(), medicoCrm, pacienteCpf);
		if(consultaRegistro.isPresent()) {
			consultaRegistro.get().setDesmarcada(false);
			consultaRegistro.get().setMotivo(null);
			return consultaRegistro.get();
		}
		
		Consulta novaConsulta = new Consulta(consulta);
		return novaConsulta;
	}

	private MedicoResponseDTO validaMedico(String crm, LocalDate data, LocalTime horario) {
		//Verifica se o médico está ativo no sistema
		Boolean achouMedico = false;
		
		if(crm == null) {
			List<MedicoAleatorioDTO> medicos = medicoClient.listaTodosMedicos().getBody();
			
			for (MedicoAleatorioDTO med : medicos) {
				try {
					achouMedico = validaDisponibilidadeMedico(data, horario, med.crm());
					return medicoClient.encontrarMedico(med.crm()).getBody();
				}
				catch (MedicoIndisponivel e) {
					
				}
			}
			
			if(!achouMedico) {
				throw new SemMedicosDisponiveis();
			}
		}
			//Criar um método para ver se médico existe
			MedicoResponseDTO medico =  medicoClient.encontrarMedico(crm).getBody();
			validaDisponibilidadeMedico(data, horario, crm);	
			return medico;
	}
	
	private PacienteResponseDTO validaPaciente(String cpf) {
		//Verifica se o paciente está ativo no sistema
		try {
		PacienteResponseDTO paciente = pacienteClient.encontrarPaciente(cpf).getBody();
		return paciente;
		}
		catch (FeignClientException e) {
			throw e;
		}
	}
	
	
	private Boolean validaDisponibilidadeMedico(LocalDate data, LocalTime horario, String crmMedico) throws MedicoIndisponivel {
		//Verifica se o médico está disponível na data e horario estabelecido
		
		Optional<Consulta> consulta = consultaRepository.findByIdsDataAndIdsHorarioAndIdsMedico(data, horario, crmMedico);
		
		if(consulta.isPresent()) {
			throw new MedicoIndisponivel();
		}
		
		return true;
	}
	
	private boolean validaUnicaConsultaDoDiaPaciente(LocalDate data, String cpfPaciente) throws JaPossuiAgendamento {
		//Verifica se o paciente já tem uma consulta no dia
		Optional<Consulta> consultaRegistro = consultaRepository.findByIdsDataAndIdsPaciente(data, cpfPaciente);
		
		if(consultaRegistro.isPresent() && !consultaRegistro.get().getDesmarcada()) {
			throw new JaPossuiAgendamento(data);
		}
		
		return true;
	}
	
	private void validaHorario(LocalTime horario, LocalDate data) throws HorarioInvalido {
		
		//Horário de funcionamento das 7 às 19. Consultas tem, exatamente, uma hora.
		
		LocalDate dataAtual = LocalDate.now();		
		Period diferencaEntreDias = Period.between(dataAtual, data);
		
		//Gerar nova exception
		if(data.isBefore(dataAtual)) {
			throw new DataInvalida();
		}
		
		
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
		if(dayOfWeek.equals(DayOfWeek.SUNDAY)) {
			throw new DiaInvalidoParaConsulta();
		}
		
	}

	public void cancelar(ConsultaCancelamentoRequestDTO cancelamento) throws ConsultaNaoMarcada, CancelamentoForaDoPrazo {
		
		validaPaciente(cancelamento.paciente());
		
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
		consulta.setDesmarcada(true);
		
		consultaRepository.save(consulta);

		PacienteResponseDTO paciente = pacienteClient.encontrarPaciente(cancelamento.paciente()).getBody(); 
		rabbitTemplate.convertAndSend("email_enviar.exchange","", new EmailDto(paciente, cancelamento));
		
	}

	public List<Consulta> listar() {
		return consultaRepository.findAll();
	}
	
}