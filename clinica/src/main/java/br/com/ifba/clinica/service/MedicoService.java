package br.com.ifba.clinica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.ifba.clinica.DTO.MedicoRequestDTO;
import br.com.ifba.clinica.DTO.MedicoResponseDTO;
import br.com.ifba.clinica.DTO.UpdateFormDTO;
import br.com.ifba.clinica.exception.MedicoNotFound;
import br.com.ifba.clinica.model.Medico;
import br.com.ifba.clinica.repository.MedicoRepository;

@Service
public class MedicoService {
	
	
	@Autowired 
	private MedicoRepository medicoRepository;

	public void cadastrarMedico(MedicoRequestDTO data) {
		Medico medico = new Medico(data);
		medicoRepository.save(medico);
	}

	public List<MedicoResponseDTO> listarMedicos(){
		
		return MedicoResponseDTO.converter(medicoRepository.findByOrderByDadosNomeAsc());
	}
	
	public ResponseEntity atualizarDados(Long id, UpdateFormDTO dados) throws MedicoNotFound{
		
		Optional<Medico> medico = medicoRepository.findById(id);
		
		if(medico.isPresent()) {
			Medico med = medico.get();
			med.setNome(dados.nome() == null ? med.getNome() : dados.nome());
			med.setTelefone(dados.telefone() == null ? med.getTelefone() : dados.telefone());
			med.setEndereco(dados.endereco() == null ? med.getEndereco() : dados.endereco());
			return  new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			throw new MedicoNotFound();
		}
	}

}
