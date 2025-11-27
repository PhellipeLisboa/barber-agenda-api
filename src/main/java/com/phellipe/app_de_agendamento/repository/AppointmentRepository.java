package com.phellipe.app_de_agendamento.repository;

import com.phellipe.app_de_agendamento.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByAgendaIdAndId(Long agendaId, Long appointmentId);
    List<Appointment> findAllByAgendaId(Long agendaId);
    Boolean existsByAgendaIdAndAppointmentDateTime(Long agendaId, LocalDateTime appointmentDateTime);
    Boolean existsByAgendaIdAndId(Long agendaId, Long id);
}
