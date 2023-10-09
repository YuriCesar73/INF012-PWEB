package com.br.paciente.controller;

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

import com.br.paciente.dto.PacienteRequestDTO;
import com.br.paciente.dto.PacienteResponseDTO;
import com.br.paciente.dto.PacienteUpdateDTO;
import com.br.paciente.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	 PacienteService servico;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<PacienteResponseDTO> cadastrarMedico(@Valid @RequestBody PacienteRequestDTO postData) {
		PacienteResponseDTO response = servico.cadastrarPaciente(postData);
		return new ResponseEntity<PacienteResponseDTO> (response, HttpStatus.CREATED);  
	}
	
	@GetMapping("/listar/")
	public ResponseEntity<List<PacienteResponseDTO>> listarPacientes(@RequestParam(required=false) Integer page) {
		//VERIFICAR A FORMA DE PASSAR A PÁGINA
		return new ResponseEntity<List<PacienteResponseDTO>>(servico.listarPacientes(page),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarDados(@PathVariable Long id, @Valid @RequestBody PacienteUpdateDTO dados){
		
		servico.atualizarDados(id, dados);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
		
//		try {
//			servico.atualizarDados(id, dados);
//			return new ResponseEntity<>(HttpStatus.ACCEPTED);
//		} catch (PacienteNotFound e) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		  catch(ValidationInvalid error) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<?> deleteMedico(@PathVariable Long id) {
		servico.deletePaciente(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
		
		
//		try {
//			servico.deletePaciente(id);
//			return new ResponseEntity<>(HttpStatus.ACCEPTED);
//		} catch (PacienteNotFound e) {
//			// TODO Auto-generated catch block
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}

	}
		
}
