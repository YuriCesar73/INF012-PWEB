package br.com.ifba.clinica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.ifba.clinica.DTO.PacienteRequestDTO;
import br.com.ifba.clinica.DTO.PacienteResponseDTO;
import br.com.ifba.clinica.DTO.PacienteUpdateDTO;
import br.com.ifba.clinica.exception.PacienteNotFound;
import br.com.ifba.clinica.exception.ValidationInvalid;
import br.com.ifba.clinica.model.Endereco;
import br.com.ifba.clinica.model.Medico;
import br.com.ifba.clinica.model.Paciente;
import br.com.ifba.clinica.repository.PacienteRepository;

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
	
	public void atualizarDados(Long id, PacienteUpdateDTO dados) throws PacienteNotFound, ValidationInvalid{
		
		try {
			this.validarDados(dados);
		} catch (ValidationInvalid error) {
			throw error;
		}
		
		//Paciente paciente = pacienteRepository.findById(id).orElseThrow(PacienteNotFound::new);
		Optional<Paciente> p = pacienteRepository.findByActiveTrueAndId(id);
		if(p.isEmpty()) {
			throw new PacienteNotFound();
		}
		
		Paciente paciente = p.get();
		
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
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(PacienteNotFound::new);
		
		paciente.setActive(false);
		pacienteRepository.save(paciente);
	}

	public void findPaciente(Long id) throws PacienteNotFound {
		Optional<Paciente> paciente = pacienteRepository.findByActiveTrueAndId(id);
		if(paciente.isEmpty()) {
			throw new PacienteNotFound();
		}
		
		PacienteResponseDTO p = new PacienteResponseDTO(paciente.get());
		
	}

}
