package com.br.consulta.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.consulta.clients.MedicoClient;
import com.br.consulta.clients.PacienteClient;
import com.br.consulta.dto.ConsultaRequestDTO;
import com.br.consulta.dto.ConsultaResponseDTO;
import com.br.consulta.model.Consulta;
import com.br.consulta.repository.ConsultaRepository;


@Service
public class ConsultaService {
	
	@Autowired
	ConsultaRepository consultaRepository;
	
	@Autowired
	MedicoClient medicoClient;
	
	@Autowired
	PacienteClient pacienteClient;

	public ConsultaResponseDTO cadastrar(ConsultaRequestDTO data) {
		 medicoClient.encontrarMedicoPorId(data.medico());
		pacienteClient.encontrarPacientePorId(data.paciente()); 
		
		Consulta consulta = new Consulta(data);
		consultaRepository.save(consulta);
		return new ConsultaResponseDTO(consulta.getData(), consulta.getHorario(), "ADICIONAR O NOME DO MÉDICO NO SERVICE");
		
	}
	
}