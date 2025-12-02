package com.phellipe.barber_agenda_api.dto.appointment;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record AppointmentPatchDto(

        //@OptionalSize(min = 4, max = 30, message = "Professional name must be between 4 and 30 characters.")
        Optional<UUID> professionalId,
        Optional<LocalDateTime> appointmentDateTime
) {
}