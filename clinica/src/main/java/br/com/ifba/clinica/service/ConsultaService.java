package br.com.ifba.clinica.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifba.clinica.DTO.ConsultaCancelamentoRequestDTO;
import br.com.ifba.clinica.DTO.ConsultaRequestDTO;
import br.com.ifba.clinica.exception.CancelamentoForaDoPrazo;
import br.com.ifba.clinica.exception.ConsultaNaoMarcada;
import br.com.ifba.clinica.exception.DiaInvalidoParaConsulta;
import br.com.ifba.clinica.exception.HorarioInvalido;
import br.com.ifba.clinica.exception.JaPossuiAgendamento;
import br.com.ifba.clinica.exception.MedicoIndisponivel;
import br.com.ifba.clinica.exception.MedicoNotFound;
import br.com.ifba.clinica.exception.PacienteNotFound;
import br.com.ifba.clinica.exception.SemMedicosDisponiveis;
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

	public void cadastrar(ConsultaRequestDTO data) {
		
		Long id;
		id = validarConsulta(data);
//		try {
//			id = validarConsulta(data);
//		} catch (DiaInvalidoParaConsulta | HorarioInvalido | MedicoNotFound | PacienteNotFound | MedicoIndisponivel
//				| JaPossuiAgendamento e) {
//			throw e;
//		}
		
		if(data.medico() == null) {
			id = consultaRepository.findRandomMedico(data.data()).orElseThrow(() -> new SemMedicosDisponiveis());
		}
		
		Consulta consulta = new Consulta(data, id);
		consultaRepository.save(consulta);
	}
	
	
	
	private Long validarConsulta(ConsultaRequestDTO data) throws DiaInvalidoParaConsulta, HorarioInvalido, MedicoNotFound, PacienteNotFound, MedicoIndisponivel, JaPossuiAgendamento, SemMedicosDisponiveis{
		Long id;
		validaDiaDaSemana(data.data().getDayOfWeek());
		validaHorario(data.horario(), data.data());
		validaPaciente(data.paciente());
		validaUnicaConsultaDoDiaPaciente(data.data(), data.paciente());
		id = validaMedico(data.medico(), data.data(), data.horario());
		
//		try {
//			validaDiaDaSemana(data.data().getDayOfWeek());
//			validaHorario(data.horario(), data.data());
//			validaPaciente(data.paciente());
//			validaUnicaConsultaDoDiaPaciente(data.data(), data.paciente());
//			id = validaMedico(data.medico(), data.data(), data.horario());
//		} catch (DiaInvalidoParaConsulta e) {
//			throw e;
//		} catch (HorarioInvalido error) {
//			throw error;
//		}
//		 catch (MedicoNotFound error) {
//			throw error;
//		}
//		 catch (PacienteNotFound error) {
//			throw error;
//		}
//		 catch(JaPossuiAgendamento error) {
//			 throw error;
//		 }
//		 catch(MedicoIndisponivel error) {
//			 throw error;
//		 }
//		 catch(SemMedicosDisponiveis error) {
//			 throw error;
//		 }
//		
		return id;
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
			throw new JaPossuiAgendamento(data);
		}
		
	}

	private Long validaMedico(Long id, LocalDate data, LocalTime horario) throws MedicoNotFound, SemMedicosDisponiveis, MedicoIndisponivel {
		//Verifica se o médico está ativo no sistema
		if(id != null) {
			try {
				medicoService.findMedicoAtivo(id);
				validaDisponibilidadeMedico(data, horario, id);
				return id;
			} catch (MedicoNotFound | MedicoIndisponivel e) {
				throw e;
			}
		}
		else { 
		
			Optional<Long> idMedico = consultaRepository.findRandomMedico(data);
			
			if(idMedico.isEmpty()) {
				throw new SemMedicosDisponiveis();
			}
			
			return idMedico.get();
			
		}
		
	}
	
	private void validaPaciente(Long id) throws PacienteNotFound {
		//Verifica se o paciente está ativo no sistema
		pacienteService.findPacienteAtivo(id);
	}
	
	private void validaHorario(LocalTime horario, LocalDate data) throws HorarioInvalido {
		
		//Horário de funcionamento das 7 às 19. Consultas tem, exatamente, uma hora.
		
		LocalDate dataAtual = LocalDate.now();		
		Period diferencaEntreDias = Period.between(dataAtual, data);
		
		if(diferencaEntreDias.getDays() < 1) {
			
			Duration diferenca = Duration.between(horario, LocalTime.now());
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
		Consulta consulta = consultaRepository.findByIdsDataAndIdsPacienteId(cancelamento.data(), cancelamento.paciente())
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
