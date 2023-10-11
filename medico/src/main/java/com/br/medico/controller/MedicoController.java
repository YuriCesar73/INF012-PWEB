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
import com.br.medico.dto.MedicoUpdateDTO;
import com.br.medico.service.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")

public class MedicoController {
	
	@Autowired
	 MedicoService servico;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<MedicoResponseDTO> cadastrarMedico(@Valid @RequestBody MedicoRequestDTO postData) {
		MedicoResponseDTO response = servico.cadastrarMedico(postData);
		return new ResponseEntity<MedicoResponseDTO> (response, HttpStatus.CREATED);  
	}
	
	@GetMapping("/listar/")
	public ResponseEntity<List<MedicoResponseDTO>> listaMedicos(@RequestParam(required=false) Integer page) {
		//VERIFICAR A FORMA DE PASSAR A PÁGINA
		return new ResponseEntity<List<MedicoResponseDTO>>(servico.listarMedicos(page),HttpStatus.OK);
	}
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<MedicoResponseDTO> listaMedicos(@PathVariable Long id) {
		return new ResponseEntity<MedicoResponseDTO>(servico.getMedico(id),HttpStatus.OK);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarDados(@PathVariable Long id, @Valid @RequestBody MedicoUpdateDTO dados){
		System.out.println(id);
		
		servico.atualizarDados(id, dados);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<?> deleteMedico(@PathVariable Long id) {
		servico.deleteMedico(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
		
//		try {
//			servico.deleteMedico(id);
//			return new ResponseEntity<>(HttpStatus.ACCEPTED);
//		} catch (MedicoNotFound e) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}

	}
		
}
