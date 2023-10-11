package com.br.medico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicoApplication.class, args);
	}

}
