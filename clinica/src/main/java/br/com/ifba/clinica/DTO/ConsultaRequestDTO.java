package br.com.ifba.clinica.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ConsultaRequestDTO(Long medico, Long paciente, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")LocalDate data, @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss") LocalTime horario) {

}
