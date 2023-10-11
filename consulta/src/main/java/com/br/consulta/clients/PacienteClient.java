package com.br.consulta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.br.consulta.clients.dto.PacienteResponseDTO;

@FeignClient("paciente-ms")
public interface PacienteClient {
	@RequestMapping(method = RequestMethod.GET, value = "/pacientes/listar/{id}")
	public ResponseEntity<PacienteResponseDTO> encontrarPacientePorId(@PathVariable Long id);
	

}
