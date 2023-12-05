package com.br.paciente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication 
@EnableFeignClients   
public class PacienteApplication {  

	public static void main(String[] args) { 
		SpringApplication.run(PacienteApplication.class, args);
	}
	
	

}
