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
import br.com.ifba.clinica.exception.MedicoNotFound;
import br.com.ifba.clinica.model.Medico;
import br.com.ifba.clinica.repository.MedicoRepository;

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
	
	public ResponseEntity atualizarDados(Long id, MedicoUpdateDTO dados) throws MedicoNotFound{
		
		Optional<Medico> medico = medicoRepository.findById(id);
		
		if(medico.isPresent()) {
			Medico med = medico.get();
			med.setNome(dados.nome() == null ? med.getNome(): dados.nome());
			med.setTelefone(dados.telefone() == null ? med.getTelefone() : dados.telefone());
			med.setEndereco(dados.endereco() == null ? med.getEndereco() : dados.endereco());
			return  new ResponseEntity<>(HttpStatus.ACCEPTED);
		}

			throw new MedicoNotFound();
	}
	
	
	public ResponseEntity deleteMedico(Long id) throws MedicoNotFound {
		Optional<Medico> medico = medicoRepository.findById(id);
		
		if(medico.isPresent()) {
			medico.get().setActive(false);
			medicoRepository.save(medico.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		throw new MedicoNotFound();
	}

}
