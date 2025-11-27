package com.phellipe.app_de_agendamento.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvalidAppointmentDateTimeException extends RuntimeException{

    public InvalidAppointmentDateTimeException() {
        super(
                String.format("It is not possible to schedule an appointment before %s",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")))
        );
    }
}
