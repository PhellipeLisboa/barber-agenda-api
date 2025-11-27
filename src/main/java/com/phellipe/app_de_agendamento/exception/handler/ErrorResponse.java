package com.phellipe.app_de_agendamento.exception.handler;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {
}
