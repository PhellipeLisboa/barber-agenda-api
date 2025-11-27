package com.phellipe.app_de_agendamento.dto.agenda;

import java.time.LocalTime;
import java.util.UUID;

public record AgendaResponseDto(
        Long id,
        String name,
        LocalTime workdayStart,
        LocalTime workdayEnd,
        UUID owner
) {
}
