package com.br.consulta.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.br.consulta.dto.ConsultaRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Consulta {

	@Column(nullable = false, name = "medico_id")
	private Long medico;
	
	@Column(nullable = false, name = "paciente_id")
	private Long paciente;
	
	@Column(nullable = false, name = "data")
	private LocalDate data;
	
	@Column(nullable = false, name = "horario")
	private LocalTime horario;
	
	@CreationTimestamp
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "CREATED_AT", updatable = false, nullable = false)
	private LocalDateTime created_at;
	
	@UpdateTimestamp
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime updated_at;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private CancelamentoConsulta motivo;	
	
	public Consulta() {
		
	}

	

	public Consulta(Long medico, Long paciente, LocalDateTime created_at, LocalDateTime updated_at,
			CancelamentoConsulta motivo) {
		super();
		this.medico = medico;
		this.paciente = paciente;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.motivo = motivo;
	}



	public Consulta(ConsultaRequestDTO data) {
		this.medico = data.medico();
		this.paciente = data.paciente();
	}


	public Consulta(ConsultaRequestDTO data, Long id) {
		this.medico = id;
		this.paciente = data.paciente();
		this.data = data.data();
		this.horario = data.horario();
	}


	public void setMedicoId(Long medicoId) {
		this.setMedicoId(medicoId); 
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}


	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}


	public LocalDateTime getUpdated_at() {
		return updated_at;
	}


	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

	public CancelamentoConsulta getMotivo() {
		return motivo;
	}

	public void setMotivo(CancelamentoConsulta status) {
		this.motivo = status;
	}
	
	
	

}
