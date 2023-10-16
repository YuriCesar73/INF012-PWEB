package com.br.consulta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.br.consulta.clients.dto.EmailDto;

@FeignClient("email-ms")
public interface EmailClient {
	@RequestMapping(method = RequestMethod.POST, value = "/send")
	public ResponseEntity<EmailDto> enviarEmail(@RequestBody EmailDto email);
}
