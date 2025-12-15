package com.phellipe.barber_agenda_api.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record AppointmentPatchDto(
        @Schema(description = "Appointment's professional id.", example = "3f1b2c9e-7a4d-4c8b-9a2f-6e1c8d5b4a90")
        Optional<UUID> professionalId,
        @Schema(description = "Appointment's date and time.", example = "2025-01-15T14:30:00")
        Optional<LocalDateTime> appointmentDateTime
) {
}