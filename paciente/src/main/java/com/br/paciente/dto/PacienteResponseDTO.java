package com.br.paciente.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.br.paciente.model.Paciente;


public record PacienteResponseDTO(String nome, String email, String cpf) {
	
	public PacienteResponseDTO(Paciente paciente) {
		this(paciente.getDados().getNome(), paciente.getDados().getEmail(), paciente.getCpf());
	}
	
	public static List<PacienteResponseDTO> converter (List<Paciente> listaPaciente) {
		return listaPaciente.stream().map(PacienteResponseDTO::new).collect(Collectors.toList()); 
	}
}