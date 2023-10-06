package br.com.ifba.clinica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifba.clinica.DTO.ConsultaRequestDTO;
import br.com.ifba.clinica.model.Consulta;
import br.com.ifba.clinica.repository.ConsultaRepository;

@Service
public class ConsultaService {
	
	@Autowired
	ConsultaRepository consultaRepository;

	public void cadastrar(ConsultaRequestDTO data) {
		
		Consulta consulta = new Consulta(data);
		consultaRepository.save(consulta);
	}

}
