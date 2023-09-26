package br.com.ifba.clinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifba.clinica.DTO.MedicoRequestDTO;
import br.com.ifba.clinica.DTO.MedicoResponseDTO;
import br.com.ifba.clinica.model.Medico;
import br.com.ifba.clinica.repository.MedicoRepository;
import br.com.ifba.clinica.service.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	@Autowired
	private MedicoRepository medicoRepository;
	
	
	@PostMapping("/cadastrar")
	public void cadastrarMedico(@RequestBody MedicoRequestDTO postData) {
		Medico medico = new Medico(postData);
		
		medicoRepository.save(medico);
	}
	
	@GetMapping("/listar")
	public List<MedicoResponseDTO> listaMedicos() {
		
		 MedicoService servico = new MedicoService();
		 return servico.listarMedicos(medicoRepository.findByOrderByDadosNomeAsc());

	}
}
