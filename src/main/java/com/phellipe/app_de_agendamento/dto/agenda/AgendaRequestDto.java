package com.phellipe.app_de_agendamento.dto.agenda;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record AgendaRequestDto(
        @Valid
        
        @NotBlank(message = "Agenda name is required.")
        @Size(min = 4, max = 30, message = "Agenda name must be between 4 and 30 characters.")
        String name,
        @NotNull(message = "Opening time is required.")
        LocalTime workdayStart,
        @NotNull(message = "Closing time is required.")
        LocalTime workdayEnd
) {
}
