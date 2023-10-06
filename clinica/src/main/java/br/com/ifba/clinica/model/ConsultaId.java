package br.com.ifba.clinica.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ConsultaId implements Serializable{
	
	@Column(name = "medico_id", nullable = false)
	private Long medicoId;
	
	@Column(name = "paciente_id", nullable = false)
	private Long pacienteId;
	
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@Column(name = "data", nullable = false)
	private LocalDate data;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
	@Column(name = "hora", nullable = false)
	private LocalTime hora;
	
	
	public ConsultaId() {
		
	}
	
	


	public ConsultaId(Long medicoId, Long pacienteId, LocalDate data, LocalTime hora) {
		super();
		this.medicoId = medicoId;
		this.pacienteId = pacienteId;
		this.data = data;
		this.hora = hora;
	}




	@Override
	public int hashCode() {
		return Objects.hash(data, hora, medicoId, pacienteId);
	}
	
	
	public Long getMedicoId() {
		return medicoId;
	}


	public void setMedicoId(Long medicoId) {
		this.medicoId = medicoId;
	}


	public Long getPacienteId() {
		return pacienteId;
	}


	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}


	public LocalDate getData() {
		return data;
	}


	public void setData(LocalDate data) {
		this.data = data;
	}


	public LocalTime getHora() {
		return hora;
	}


	public void setHora(LocalTime hora) {
		this.hora = hora;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsultaId other = (ConsultaId) obj;
		return Objects.equals(data, other.data) && Objects.equals(hora, other.hora)
				&& Objects.equals(medicoId, other.medicoId) && Objects.equals(pacienteId, other.pacienteId);
	}


	

}
