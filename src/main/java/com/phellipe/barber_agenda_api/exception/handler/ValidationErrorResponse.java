package com.phellipe.barber_agenda_api.exception.handler;

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
