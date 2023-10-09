package com.br.paciente.controller.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.br.paciente.exception.PacienteNotFound;
import com.br.paciente.exception.ValidationInvalid;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(PacienteNotFound.class)
	public ResponseEntity<StandardError> pacienteNotFound(PacienteNotFound e, HttpServletRequest request) {
		String error = "Paciente não encontrado";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ValidationInvalid.class)
	public ResponseEntity<StandardError> validacaoInvalida(ValidationInvalid e, HttpServletRequest request){
		String error = "Dados invalidos. Os campos de email e cpf não podem ser alterados";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);	
		}
}