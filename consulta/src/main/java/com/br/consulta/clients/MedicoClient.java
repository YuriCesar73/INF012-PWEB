package com.br.consulta.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.br.consulta.clients.dto.MedicoResponseDTO;
import com.br.consulta.clients.dto.MedicoAleatorioDTO;

@FeignClient("medico-ms")
public interface MedicoClient {
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/listar/{crm}")
	public ResponseEntity<MedicoResponseDTO> encontrarMedico(@PathVariable String crm);
	
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/listar/all")
	public ResponseEntity<List<MedicoAleatorioDTO>> listaTodosMedicos();

}

