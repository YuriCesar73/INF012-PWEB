package com.br.medico.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.br.medico.model.Especialidade;
import com.br.medico.model.Medico;

public record MedicoResponseToMs(Long id, String nome, String email, String crm, String especialidade) {

	public MedicoResponseToMs(Medico medico) {
		this(medico.getId(), medico.getDados().getNome(), medico.getDados().getEmail(), medico.getCrm(), medico.getEspecialidade().toString());
	}
	
	public static List<MedicoResponseToMs> converter (List<Medico> listaMedicos) {
		return listaMedicos.stream().map(MedicoResponseToMs::new).collect(Collectors.toList()); 
	}
}
