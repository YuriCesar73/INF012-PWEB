package com.br.medico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.medico.dto.MedicoRequestDTO;
import com.br.medico.dto.MedicoResponseDTO;
import com.br.medico.dto.MedicoResponseToMs;
import com.br.medico.dto.MedicoUpdateDTO;
import com.br.medico.exception.CrmJaCadastrado;
import com.br.medico.exception.MedicoNotFound;
import com.br.medico.exception.ValidationInvalid;
import com.br.medico.model.Endereco;
import com.br.medico.model.Medico;
import com.br.medico.repository.MedicoRepository;

@Service
public class MedicoService {
	
	
	@Autowired 
	private MedicoRepository medicoRepository;

	public MedicoResponseDTO cadastrarMedico(MedicoRequestDTO data) {
		
		Medico medico = medicoRepository.findByCrm(data.crm()).orElse(new Medico(data));

		if(medico.getActive()) {
			throw new CrmJaCadastrado(data.crm());
		}
		medico.setActive(true);
		medicoRepository.save(medico);
		return new MedicoResponseDTO(medico);
	}

	public List<MedicoResponseDTO> listarMedicos(Integer page){
		
		return MedicoResponseDTO.converter(medicoRepository.findByActiveTrueOrderByDadosNomeAsc(PageRequest.of(page == null ? 0 : page, 10)));
	}
	
	public List<MedicoResponseToMs> listaTodosMedicos(){
		return MedicoResponseToMs.converter(medicoRepository.findByActiveTrueOrderByDadosNomeAsc());
		
	}
	
	public MedicoResponseDTO getMedico(String crm){
		Medico medico = medicoRepository.findByActiveTrueAndCrm(crm).orElseThrow(() -> new MedicoNotFound(crm));
		return new MedicoResponseDTO(medico);
		
		
	}
	public MedicoResponseDTO atualizarDados(String crm, MedicoUpdateDTO dados) throws MedicoNotFound, ValidationInvalid{
		
		try {
			this.validarDados(dados);
		} catch (ValidationInvalid error) {
			throw error;
		}
		
		
		Medico medico = medicoRepository.findByActiveTrueAndCrm(crm).orElseThrow(() -> new MedicoNotFound(crm));
		
		medico.setNome(dados.nome() == null ? medico.getNome(): dados.nome());
		medico.setTelefone(dados.telefone() == null ? medico.getTelefone() : dados.telefone());
		medico.setEndereco(dados.endereco() == null ? medico.getEndereco() : new Endereco(dados.endereco()));
		medicoRepository.save(medico);
		return new MedicoResponseDTO(medico);

	}
	
	
	private void validarDados(MedicoUpdateDTO dados) throws ValidationInvalid {
		if(!(dados.especialidade() == null) || !(dados.email() == null) || !(dados.crm() == null)) {
			throw new ValidationInvalid();
		}
		
	}

	public void deleteMedico(String crm) throws MedicoNotFound {
		Medico medico = medicoRepository.findByActiveTrueAndCrm(crm).orElseThrow(() -> new MedicoNotFound(crm));
		medico.setActive(false);
		medicoRepository.save(medico);
	}
	
	public MedicoResponseDTO findMedicoAtivo(String crm) throws MedicoNotFound {
		Optional<Medico> medico = medicoRepository.findByActiveTrueAndCrm(crm);
		if(medico.isEmpty()) {
			throw new MedicoNotFound(crm);
		}
		MedicoResponseDTO med = new MedicoResponseDTO(medico.get());
		return med;
	}


}