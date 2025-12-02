package com.phellipe.barber_agenda_api.dto.agenda;

import jakarta.validation.Valid;

import java.time.LocalTime;
import java.util.Optional;

public record AgendaPatchDto(

        @Valid

        Optional<LocalTime> workdayStart,
        Optional<LocalTime> workdayEnd
) {
}
