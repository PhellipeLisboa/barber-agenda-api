package com.phellipe.app_de_agendamento.dto.appointment;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDto(
        Long id,
        Long agendaId,
        UUID customerId,
        String customerName,
        UUID professionalId,
        String professionalName,
        LocalDateTime appointmentDateTime
) {
}

