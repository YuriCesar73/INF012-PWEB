package br.com.ifba.clinica.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.ifba.clinica.DTO.ConsultaRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Consulta {

	@EmbeddedId
	private ConsultaId ids;
	
	@ManyToOne
	@MapsId("medico_id")
	@JoinColumn(nullable = false)
	private Medico medico;
	
	@ManyToOne
	@MapsId("paciente_id")
	@JoinColumn(nullable = false)
	private Paciente paciente;
	
	@CreationTimestamp
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "CREATED_AT", updatable = false, nullable = false)
	private LocalDateTime created_at;
	
	@UpdateTimestamp
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime updated_at;
	
	public Consulta() {
		
	}

	public Consulta(ConsultaId ids, Medico medico, Paciente paciente, LocalDateTime created_at, LocalDateTime updated_at) {
		super();
		this.ids = ids;
		this.medico = medico;
		this.paciente = paciente;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Consulta(ConsultaRequestDTO data) {
		this.ids = new ConsultaId(data.medico(), data.paciente(), data.data(), data.horario());
	}


	public Consulta(ConsultaRequestDTO data, Long id) {
		this.ids = new ConsultaId(id, data.paciente(), data.data(), data.horario());
	}

	public Long getMedicoId() {
		return ids.getMedicoId();
	}


	public void setMedicoId(Long medicoId) {
		this.setMedicoId(medicoId); 
	}


	public Long getPacienteId() {
		return ids.getPacienteId();
	}


	public void setPacienteId(Long pacienteId) {
		this.ids.setPacienteId(pacienteId);
	}


	public LocalDate getData() {
		return ids.getData();
	}


	public void setData(LocalDate data) {
		this.ids.setData(data);
	}


	public LocalTime getHora() {
		return ids.getHora();
	}


	public void setHora(LocalTime hora) {
		this.ids.setHora(hora);
	}


	public ConsultaId getIds() {
		return ids;
	}


	public void setIds(ConsultaId ids) {
		this.ids = ids;
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
	
	

}

