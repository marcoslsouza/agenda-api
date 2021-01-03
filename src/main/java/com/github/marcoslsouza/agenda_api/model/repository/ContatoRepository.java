package com.github.marcoslsouza.agenda_api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.marcoslsouza.agenda_api.model.entity.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
