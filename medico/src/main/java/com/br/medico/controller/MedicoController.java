package com.br.medico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.medico.dto.MedicoRequestDTO;
import com.br.medico.dto.MedicoResponseDTO;
import com.br.medico.dto.MedicoResponseToMs;
import com.br.medico.dto.MedicoUpdateDTO;
import com.br.medico.service.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")

public class MedicoController {
	
	@Autowired
	private MedicoService servico;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<MedicoResponseDTO> cadastrarMedico(@Valid @RequestBody MedicoRequestDTO postData) {
		MedicoResponseDTO response = servico.cadastrarMedico(postData);
		return new ResponseEntity<MedicoResponseDTO> (response, HttpStatus.CREATED);  
	}
	
	@GetMapping("/listar/")
	public ResponseEntity<List<MedicoResponseDTO>> listaMedicos(@RequestParam(required=false) Integer page) {
		return new ResponseEntity<List<MedicoResponseDTO>>(servico.listarMedicos(page),HttpStatus.OK);
	}
	
	@GetMapping("/listar/{crm}")
	public ResponseEntity<MedicoResponseDTO> listaMedicos(@PathVariable String crm) {
		return new ResponseEntity<MedicoResponseDTO>(servico.getMedico(crm),HttpStatus.OK);
	}
	
	//Rota não utilizada. Feita para debugar
	@GetMapping("/listar/all")
	public ResponseEntity<List<MedicoResponseToMs>> listAll() {
		return new ResponseEntity<List<MedicoResponseToMs>>(servico.listaTodosMedicos(), HttpStatus.OK);
	}
	
	@PutMapping("/atualizar/{crm}")
	public ResponseEntity<MedicoResponseDTO> atualizarDados(@PathVariable String crm, @Valid @RequestBody MedicoUpdateDTO dados){		
		return new ResponseEntity<MedicoResponseDTO>(servico.atualizarDados(crm, dados),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/apagar/{crm}")
	public ResponseEntity<HttpStatus> deleteMedico(@PathVariable String crm) {
		servico.deleteMedico(crm);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
		
}
