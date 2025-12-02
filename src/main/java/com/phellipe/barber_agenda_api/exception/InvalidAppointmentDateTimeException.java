package com.phellipe.barber_agenda_api.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvalidAppointmentDateTimeException extends RuntimeException{

    public InvalidAppointmentDateTimeException() {
        super(
                String.format("It is not possible to schedule an appointment before %s",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")))
        );
    }

    public InvalidAppointmentDateTimeException(String userName, LocalDateTime appointmentDateTime) {
        super(
                String.format("User %s already have an appointment scheduled at %s on %s",
                        userName,
                        appointmentDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        appointmentDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        );
    }
}
