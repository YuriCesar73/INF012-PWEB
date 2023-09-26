package br.com.ifba.clinica.service;

import java.util.List;

import br.com.ifba.clinica.DTO.MedicoResponseDTO;
import br.com.ifba.clinica.model.Medico;

public class MedicoService {
	
	
	public List<MedicoResponseDTO> listarMedicos(List<Medico> lista){
		return MedicoResponseDTO.converter(lista);
	}

}
