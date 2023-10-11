package com.br.consulta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.br.consulta.clients.dto.MedicoResponseDTO;

@FeignClient("medico-ms")
public interface MedicoClient {
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/listar/{id}")
	public ResponseEntity<MedicoResponseDTO> encontrarMedicoPorId(@PathVariable Long id);

}

