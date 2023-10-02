package br.com.ifba.clinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifba.clinica.DTO.MedicoRequestDTO;
import br.com.ifba.clinica.DTO.MedicoResponseDTO;
import br.com.ifba.clinica.DTO.UpdateFormDTO;
import br.com.ifba.clinica.exception.MedicoNotFound;
import br.com.ifba.clinica.service.MedicoService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/medicos")

public class MedicoController {
	
	@Autowired
	 MedicoService servico;
	
	@PostMapping("/cadastrar")
	public ResponseEntity cadastrarMedico(@Valid @RequestBody MedicoRequestDTO postData) {
		
		servico.cadastrarMedico(postData);
		return new ResponseEntity<>(HttpStatus.CREATED);  
	}
	
	@GetMapping("/listar/")
	public ResponseEntity listaMedicos(@RequestParam(required=false) Integer page) {
		//VERIFICAR A FORMA DE PASSAR A PÁGINA
		return new ResponseEntity<List<MedicoResponseDTO>>(servico.listarMedicos(page),HttpStatus.CREATED);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity atualizarDados(@PathVariable Long id, @RequestBody UpdateFormDTO dados) {
		try {
			servico.atualizarDados(id, dados);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (MedicoNotFound e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity deleteMedico(@PathVariable Long id) {
		try {
			return servico.deleteMedico(id);
		} catch (MedicoNotFound e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
		
}

