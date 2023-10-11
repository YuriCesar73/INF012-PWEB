package com.br.consulta.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.Column;

public class ChaveComposta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, name = "medico_id")
	private Long medico;
	
	@Column(nullable = false, name = "paciente_id")
	private Long paciente;
	
	@Column(nullable = false, name = "data")
	private LocalDate data;
	
	@Column(nullable = false, name = "horario")
	private LocalTime horario;

	public ChaveComposta(Long medico, Long paciente, LocalDate data, LocalTime horario) {
		this.medico = medico;
		this.paciente = paciente;
		this.data = data;
		this.horario = horario;
	}

	public ChaveComposta() {
		
	}

	public Long getMedico() {
		return medico;
	}

	public void setMedico(Long medico) {
		this.medico = medico;
	}

	public Long getPaciente() {
		return paciente;
	}

	public void setPaciente(Long paciente) {
		this.paciente = paciente;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, horario, medico, paciente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChaveComposta other = (ChaveComposta) obj;
		return Objects.equals(data, other.data) && Objects.equals(horario, other.horario)
				&& Objects.equals(medico, other.medico) && Objects.equals(paciente, other.paciente);
	}
	
	
	
}
