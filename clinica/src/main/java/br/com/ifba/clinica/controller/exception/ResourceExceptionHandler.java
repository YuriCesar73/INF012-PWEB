package br.com.ifba.clinica.controller.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.ifba.clinica.exception.CancelamentoForaDoPrazo;
import br.com.ifba.clinica.exception.ConsultaNaoMarcada;
import br.com.ifba.clinica.exception.DiaInvalidoParaConsulta;
import br.com.ifba.clinica.exception.HorarioInvalido;
import br.com.ifba.clinica.exception.JaPossuiAgendamento;
import br.com.ifba.clinica.exception.MedicoIndisponivel;
import br.com.ifba.clinica.exception.MedicoNotFound;
import br.com.ifba.clinica.exception.PacienteNotFound;
import br.com.ifba.clinica.exception.SemMedicosDisponiveis;
import br.com.ifba.clinica.exception.ValidationInvalid;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(MedicoNotFound.class)
	public ResponseEntity<StandardError> medicoNotFound(MedicoNotFound e, HttpServletRequest request){
		String error = "Médico não encontrado";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);	
		}
	
	@ExceptionHandler(PacienteNotFound.class)
	public ResponseEntity<StandardError> pacienteNotFound(PacienteNotFound e, HttpServletRequest request) {
		String error = "Paciente não encontrado";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	
	@ExceptionHandler(ValidationInvalid.class)
	public ResponseEntity<StandardError> validacaoInvalida(ValidationInvalid e, HttpServletRequest request){
		String error = "Médico não encontrado";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);	
		}
	
	@ExceptionHandler(ConsultaNaoMarcada.class)
	public ResponseEntity<StandardError> consultaNaoMarcada(ConsultaNaoMarcada e, HttpServletRequest request){
		String error = "Essa consulta não foi encontrada na data e horário inseridos";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(CancelamentoForaDoPrazo.class)
	public ResponseEntity<StandardError> cancelamentoForaDoPrazo(CancelamentoForaDoPrazo e, HttpServletRequest request){
		String error = "O cancelamento foi feito em um período menor que 24 horas";
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(JaPossuiAgendamento.class)
	public ResponseEntity<StandardError> jaPossuiAgendamento(JaPossuiAgendamento e, HttpServletRequest request){
		String error = "Já existe uma consulta marcada para essa data";
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MedicoIndisponivel.class)
	public ResponseEntity<StandardError> medicoIndisponivel(MedicoIndisponivel e, HttpServletRequest request){
		String error = "Médico já tem consulta na data e horário escolhidos";
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DiaInvalidoParaConsulta.class)
	public ResponseEntity<StandardError> diaInvalidoParaConsulta(DiaInvalidoParaConsulta e, HttpServletRequest request){
		String error = "Não é possível marcar uma consulta no domingo";
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(HorarioInvalido.class)
	public ResponseEntity<StandardError> horarioInvalido(HorarioInvalido e, HttpServletRequest request){
		String error = "Consultas só podem ser marcadas a partir das 7 até 19 horas. Cada Consulta tem exatamente 1 hora";
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(SemMedicosDisponiveis.class)
	public ResponseEntity<StandardError> semMedicosDisponiveis(SemMedicosDisponiveis e, HttpServletRequest request){
		String error = "Não há médicos disponíveis para a data selecionada";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
}
