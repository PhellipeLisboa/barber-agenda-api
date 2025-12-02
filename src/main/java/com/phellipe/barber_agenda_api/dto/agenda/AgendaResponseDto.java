package com.phellipe.barber_agenda_api.dto.agenda;

import java.time.LocalTime;

public record AgendaResponseDto(
        LocalTime workdayStart,
        LocalTime workdayEnd
) {
}
