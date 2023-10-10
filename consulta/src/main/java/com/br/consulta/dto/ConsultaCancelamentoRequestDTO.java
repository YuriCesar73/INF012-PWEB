package com.br.consulta.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.br.consulta.model.CancelamentoConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;


public record ConsultaCancelamentoRequestDTO(Long paciente, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")LocalDate data, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm") LocalTime horario, CancelamentoConsulta motivo) {

}