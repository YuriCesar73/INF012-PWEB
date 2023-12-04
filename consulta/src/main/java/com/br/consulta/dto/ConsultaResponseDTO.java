package com.br.consulta.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.br.consulta.model.Consulta;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ConsultaResponseDTO(@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy") LocalDate data, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")  LocalTime horario, String medico, String paciente) {

	
	public ConsultaResponseDTO(Consulta consulta) {
		this(consulta.getData(), consulta.getHorario(), consulta.getMedico(), consulta.getPaciente());	
	}
	
	public static List<ConsultaResponseDTO> converter (List<Consulta> listaConsulta) {
		return listaConsulta.stream().map(ConsultaResponseDTO::new).collect(Collectors.toList()); 
	}

}