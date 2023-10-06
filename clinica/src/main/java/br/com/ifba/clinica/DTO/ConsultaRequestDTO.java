package br.com.ifba.clinica.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

public record ConsultaRequestDTO(Long medico, @NotBlank Long paciente, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")LocalDate data, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm") LocalTime horario) {

}
