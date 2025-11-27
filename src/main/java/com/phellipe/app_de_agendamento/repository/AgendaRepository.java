package com.phellipe.app_de_agendamento.repository;

import com.phellipe.app_de_agendamento.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}
