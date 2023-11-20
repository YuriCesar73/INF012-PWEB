package com.br.email.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.br.email.dto.EmailDto;
import com.br.email.service.EmailService;

@Component
public class ConsultaListener {
	
	 @Autowired
	 private EmailService emailService;
	 
	  @RabbitListener(queues = "consulta.email_enviar")
	    public void recebeMensagem(@Payload EmailDto email) {
		  emailService.sendEmail(email);
	  }

}
