package br.com.ifba.clinica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.ifba.clinica.DTO.MedicoRequestDTO;
import br.com.ifba.clinica.DTO.MedicoResponseDTO;
import br.com.ifba.clinica.DTO.MedicoUpdateDTO;
import br.com.ifba.clinica.DTO.PacienteRequestDTO;
import br.com.ifba.clinica.DTO.PacienteResponseDTO;
import br.com.ifba.clinica.DTO.PacienteUpdateDTO;
import br.com.ifba.clinica.exception.MedicoNotFound;
import br.com.ifba.clinica.exception.PacienteNotFound;
import br.com.ifba.clinica.exception.ValidationInvalid;
import br.com.ifba.clinica.model.Endereco;
import br.com.ifba.clinica.model.Medico;
import br.com.ifba.clinica.model.Paciente;
import br.com.ifba.clinica.repository.MedicoRepository;
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
		
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		
		if(paciente.isEmpty()) {
			throw new PacienteNotFound();
		}


		Paciente p = paciente.get();
		p.setNome(dados.nome() == null ? p.getNome(): dados.nome());
		p.setTelefone(dados.telefone() == null ? p.getTelefone() : dados.telefone());
		p.setEndereco(dados.endereco() == null ? p.getEndereco() : new Endereco(dados.endereco()));
		pacienteRepository.save(p);

	}
	
	
	private void validarDados(PacienteUpdateDTO dados) throws ValidationInvalid {
		if(!(dados.email() == null) || !(dados.cpf() == null)) {
			throw new ValidationInvalid();
		}
		
	}

	public void deletePaciente(Long id) throws PacienteNotFound {
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		
		if(paciente.isEmpty()) {
			throw new PacienteNotFound();
		}
		
		paciente.get().setActive(false);
		pacienteRepository.save(paciente.get());
	}

}
