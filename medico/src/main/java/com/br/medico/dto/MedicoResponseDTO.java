package com.br.medico.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.br.medico.model.Especialidade;
import com.br.medico.model.Medico;


public record MedicoResponseDTO(String nome, String email, String crm, Especialidade especialidade) {
	
	public MedicoResponseDTO(Medico medico) {
		this(medico.getDados().getNome(), medico.getDados().getEmail(), medico.getCrm(), medico.getEspecialidade());
	}

	public static List<MedicoResponseDTO> converter (List<Medico> listaMedicos) {
		return listaMedicos.stream().map(MedicoResponseDTO::new).collect(Collectors.toList()); 
	}
	
}
 