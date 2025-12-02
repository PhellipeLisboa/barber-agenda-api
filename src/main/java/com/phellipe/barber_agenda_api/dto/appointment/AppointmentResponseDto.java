package com.phellipe.barber_agenda_api.dto.appointment;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDto(
        Long id,
        UUID customerId,
        String customerName,
        UUID professionalId,
        String professionalName,
        LocalDateTime appointmentDateTime
) {
}

