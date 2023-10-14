package com.br.medico.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.medico.dto.MedicoRequestDTO;
import com.br.medico.dto.MedicoResponseDTO;
import com.br.medico.dto.MedicoResponseToMs;
import com.br.medico.dto.MedicoUpdateDTO;
import com.br.medico.exception.MedicoNotFound;
import com.br.medico.exception.ValidationInvalid;
import com.br.medico.model.Endereco;
import com.br.medico.model.Especialidade;
import com.br.medico.model.Medico;
import com.br.medico.repository.MedicoRepository;

@Service
public class MedicoService {
	
	
	@Autowired 
	private MedicoRepository medicoRepository;

	public MedicoResponseDTO cadastrarMedico(MedicoRequestDTO data) {
		Medico medico = new Medico(data);
		medicoRepository.save(medico);
		return new MedicoResponseDTO(medico);
	}

	public List<MedicoResponseDTO> listarMedicos(Integer page){
		
		return MedicoResponseDTO.converter(medicoRepository.findByActiveTrueOrderByDadosNomeAsc(PageRequest.of(page == null ? 0 : page, 10)));
	}
	
	public List<MedicoResponseToMs> listaTodosMedicos(){
		return MedicoResponseToMs.converter(medicoRepository.findByActiveTrueOrderByDadosNomeAsc());
		
	}
	
	public MedicoResponseDTO getMedico(Long id){
		Medico medico = medicoRepository.findByActiveTrueAndId(id).orElseThrow(() -> new MedicoNotFound(id));
		return new MedicoResponseDTO(medico);
		
		
	}
	public void atualizarDados(Long id, MedicoUpdateDTO dados) throws MedicoNotFound, ValidationInvalid{
		
		try {
			this.validarDados(dados);
		} catch (ValidationInvalid error) {
			throw error;
		}
		
		
		Medico medico = medicoRepository.findByActiveTrueAndId(id).orElseThrow(() -> new MedicoNotFound(id));
		
		medico.setNome(dados.nome() == null ? medico.getNome(): dados.nome());
		medico.setTelefone(dados.telefone() == null ? medico.getTelefone() : dados.telefone());
		medico.setEndereco(dados.endereco() == null ? medico.getEndereco() : new Endereco(dados.endereco()));
		medicoRepository.save(medico);

	}
	
	
	private void validarDados(MedicoUpdateDTO dados) throws ValidationInvalid {
		if(!(dados.especialidade() == null) || !(dados.email() == null) || !(dados.crm() == null)) {
			throw new ValidationInvalid();
		}
		
	}

	public void deleteMedico(Long id) throws MedicoNotFound {
		Medico medico = medicoRepository.findById(id).orElseThrow(() -> new MedicoNotFound(id));

		
		medico.setActive(false);
		medicoRepository.save(medico);
	}
	
	public MedicoResponseDTO findMedicoAtivo(Long id) throws MedicoNotFound {
		Optional<Medico> medico = medicoRepository.findByActiveTrueAndId(id);
		if(medico.isEmpty()) {
			throw new MedicoNotFound(id);
		}
		MedicoResponseDTO med = new MedicoResponseDTO(medico.get());
		return med;
	}


}