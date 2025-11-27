package com.phellipe.app_de_agendamento.dto.agenda;

import com.phellipe.app_de_agendamento.validation.OptionalSize;
import jakarta.validation.Valid;

import java.time.LocalTime;
import java.util.Optional;

public record AgendaPatchDto(

        @Valid

        @OptionalSize(min = 4, max = 30, message = "Agenda name must be between 4 and 30 characters.")
        Optional<String> name,
        Optional<LocalTime> workdayStart,
        Optional<LocalTime> workdayEnd
) {
}
