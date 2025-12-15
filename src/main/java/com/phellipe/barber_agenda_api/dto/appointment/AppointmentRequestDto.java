package com.phellipe.barber_agenda_api.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequestDto(

        @Valid

        @NotNull(message = "Customer id is required.")
        @Schema(description = "Appointment's customer id.", example = "3f1b2c9e-7a4d-4c8b-9a2f-6e1c8d5b4a90")
        UUID customerId,
        @NotNull(message = "Professional id is required.")
        @Schema(description = "Appointment's professional id.", example = "3f1b2c9e-7a4d-4c8b-9a2f-6e1c8d5b4a90")
        UUID professionalId,
        @NotNull(message = "Appointment date and time are required.")
        @Schema(description = "Appointment's date and time.", example = "2025-01-15T14:30:00")
        LocalDateTime appointmentDateTime
) {
}