package br.com.ifba.clinica.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ConsultaResponseDTO(@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy") LocalDate data, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")  LocalTime horario, String medico) {

}
