package com.phellipe.app_de_agendamento.exception.handler;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        String path,
        List<String> errors

) {

}
