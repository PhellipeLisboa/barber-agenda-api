package com.phellipe.barber_agenda_api.exception.handler;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {
}
