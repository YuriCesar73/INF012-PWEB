package br.com.ifba.clinica.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifba.clinica.model.CancelamentoConsulta;

public record ConsultaCancelamentoRequestDTO(Long paciente, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")LocalDate data, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm") LocalTime horario, CancelamentoConsulta motivo) {

}
