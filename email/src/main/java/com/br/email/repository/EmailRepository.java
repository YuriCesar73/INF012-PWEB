package com.br.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.email.model.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{

}
