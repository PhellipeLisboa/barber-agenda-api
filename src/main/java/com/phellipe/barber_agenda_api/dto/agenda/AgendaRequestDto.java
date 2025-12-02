package com.phellipe.barber_agenda_api.dto.agenda;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record AgendaRequestDto(
        @Valid

        @NotNull(message = "Opening time is required.")
        LocalTime workdayStart,
        @NotNull(message = "Closing time is required.")
        LocalTime workdayEnd
) {
}
