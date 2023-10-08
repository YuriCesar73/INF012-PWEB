package br.com.ifba.clinica.controller.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.ifba.clinica.exception.MedicoNotFound;
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
	
	
	@ExceptionHandler(ValidationInvalid.class)
	public ResponseEntity<StandardError> validacaoInvalida(ValidationInvalid e, HttpServletRequest request){
		String error = "Médico não encontrado";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);	
		}
}
