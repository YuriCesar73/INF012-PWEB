package br.com.ifba.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifba.clinica.DTO.ConsultaRequestDTO;
import br.com.ifba.clinica.exception.DiaInvalidoParaConsulta;
import br.com.ifba.clinica.service.ConsultaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
	
	@Autowired
	ConsultaService service;
	
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Object> cadastrarConsulta(@RequestBody @Valid ConsultaRequestDTO consulta) {
		try {
			service.cadastrar(consulta);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
