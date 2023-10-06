package br.com.ifba.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifba.clinica.DTO.ConsultaRequestDTO;
import br.com.ifba.clinica.model.Consulta;
import br.com.ifba.clinica.repository.ConsultaRepository;
import br.com.ifba.clinica.service.ConsultaService;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
	
	@Autowired
	ConsultaService service;
	
	
	@PostMapping("/cadastrar")
	public void cadastrarConsulta(@RequestBody ConsultaRequestDTO consulta) {
		System.out.println(consulta);
		
		service.cadastrar(consulta);
	}

}
