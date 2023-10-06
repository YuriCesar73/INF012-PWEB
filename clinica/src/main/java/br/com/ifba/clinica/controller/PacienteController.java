package br.com.ifba.clinica.controller;

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

import br.com.ifba.clinica.DTO.PacienteRequestDTO;
import br.com.ifba.clinica.DTO.PacienteResponseDTO;
import br.com.ifba.clinica.DTO.PacienteUpdateDTO;
import br.com.ifba.clinica.exception.PacienteNotFound;
import br.com.ifba.clinica.exception.ValidationInvalid;
import br.com.ifba.clinica.service.PacienteService;
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
		return new ResponseEntity<List<PacienteResponseDTO>>(servico.listarPacientes(page),HttpStatus.CREATED);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarDados(@PathVariable Long id, @Valid @RequestBody PacienteUpdateDTO dados){
		
		try {
			servico.atualizarDados(id, dados);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (PacienteNotFound e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		  catch(ValidationInvalid error) {
			return new ResponseEntity<>(error.getError(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<?> deleteMedico(@PathVariable Long id) {
		try {
			servico.deletePaciente(id);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (PacienteNotFound e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
		
}

