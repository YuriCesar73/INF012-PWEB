package br.com.ifba.clinica.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;

public record ConsultaRequestDTO(Long medico, Long paciente, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")LocalDate data, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm") LocalTime horario) {

}
