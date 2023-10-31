package com.br.medico.controller.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.br.medico.exception.CrmJaCadastrado;
import com.br.medico.exception.MedicoNotFound;
import com.br.medico.exception.ValidationInvalid;

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
		String error = "Dados invalidos. Os campos de email, especialidade e crm não podem ser alterados";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);	
		}
	
	@ExceptionHandler(CrmJaCadastrado.class)
	public ResponseEntity<StandardError> validacaoInvalida(CrmJaCadastrado e, HttpServletRequest request){
		String error = "O Crm inserido já está cadastrado.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);	
		}
	
	
}