package com.br.paciente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.paciente.dto.PacienteRequestDTO;
import com.br.paciente.dto.PacienteResponseDTO;
import com.br.paciente.dto.PacienteUpdateDTO;
import com.br.paciente.exception.CpfJaCadastrado;
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
		Paciente paciente = pacienteRepository.findByCpf(data.cpf()).orElse(new Paciente(data));
		if(paciente.getActive()) {
			throw new CpfJaCadastrado(data.cpf());
		}
		paciente.setActive(true);
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
	
	public PacienteResponseDTO atualizarDados(Long id, PacienteUpdateDTO dados) throws PacienteNotFound, ValidationInvalid{
		
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
		
		return new PacienteResponseDTO(paciente);

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

	

}