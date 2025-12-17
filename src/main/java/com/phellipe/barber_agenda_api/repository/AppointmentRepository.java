package com.phellipe.barber_agenda_api.repository;

import com.phellipe.barber_agenda_api.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<List<Appointment>> findByAppointmentDateTime(LocalDateTime appointmentDateTime);
    List<Appointment> findByCustomerId(UUID customerId);
    List<Appointment> findByProfessionalId(UUID professionalID);
}
