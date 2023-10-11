package com.br.paciente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.paciente.dto.PacienteRequestDTO;
import com.br.paciente.dto.PacienteResponseDTO;
import com.br.paciente.dto.PacienteUpdateDTO;
import com.br.paciente.exception.PacienteNotFound;
import com.br.paciente.exception.ValidationInvalid;
import com.br.paciente.model.Endereco;
import com.br.paciente.model.Paciente;
import com.br.paciente.repository.PacienteRepository;

@Service
public class PacienteService {
	
	@Autowired 
	private PacienteRepository pacienteRepository;

	public PacienteResponseDTO cadastrarPaciente(PacienteRequestDTO data) {
		Paciente paciente = new Paciente(data);
		pacienteRepository.save(paciente);
		return new PacienteResponseDTO(paciente);
	}

	public List<PacienteResponseDTO> listarPacientes(Integer page){
		
		return PacienteResponseDTO.converter(pacienteRepository.findByActiveTrueOrderByDadosNomeAsc(PageRequest.of(page == null ? 0 : page, 10)));
	}
	
	public PacienteResponseDTO getPaciente(Long id) {
		Paciente paciente = pacienteRepository.findByActiveTrueAndId(id).orElseThrow(() -> new PacienteNotFound(id));
		return new PacienteResponseDTO(paciente);
	}
	
	public void atualizarDados(Long id, PacienteUpdateDTO dados) throws PacienteNotFound, ValidationInvalid{
		
		try {
			this.validarDados(dados);
		} catch (ValidationInvalid error) {
			throw error;
		}
		
		Paciente paciente = pacienteRepository.findByActiveTrueAndId(id).orElseThrow(() -> new PacienteNotFound(id));
		paciente.setNome(dados.nome() == null ? paciente.getNome(): dados.nome());
		paciente.setTelefone(dados.telefone() == null ? paciente.getTelefone() : dados.telefone());
		paciente.setEndereco(dados.endereco() == null ? paciente.getEndereco() : new Endereco(dados.endereco()));
		pacienteRepository.save(paciente);

	}
	
	
	private void validarDados(PacienteUpdateDTO dados) throws ValidationInvalid {
		if(!(dados.email() == null) || !(dados.cpf() == null)) {
			throw new ValidationInvalid();
		}
		
	}

	public void deletePaciente(Long id) throws PacienteNotFound {
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new PacienteNotFound(id));
		
		paciente.setActive(false);
		pacienteRepository.save(paciente);
	}

	public void findPacienteAtivo(Long id) throws PacienteNotFound {
		Optional<Paciente> paciente = pacienteRepository.findByActiveTrueAndId(id);
		if(paciente.isEmpty()) {
			throw new PacienteNotFound(id);
		}
		
		PacienteResponseDTO p = new PacienteResponseDTO(paciente.get());
		
	}

}