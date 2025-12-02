package com.phellipe.barber_agenda_api.dto.appointment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequestDto(

        @Valid

        @NotNull(message = "Customer id is required.")
        //@Size(min = 4, max = 30, message = "Customer name must be between 4 and 30 characters.")
        UUID customerId,
        @NotNull(message = "Professional id is required.")
        //@Size(min = 4, max = 30, message = "Professional name must be between 4 and 30 characters.")
        UUID professionalId,
        @NotNull(message = "Appointment date and time are required.")
        LocalDateTime appointmentDateTime
) {
}