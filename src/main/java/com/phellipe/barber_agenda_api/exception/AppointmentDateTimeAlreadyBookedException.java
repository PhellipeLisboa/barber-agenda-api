package com.phellipe.barber_agenda_api.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDateTimeAlreadyBookedException extends RuntimeException{

    public AppointmentDateTimeAlreadyBookedException(String professionalName ,LocalDateTime appointmentDateTime) {

        super(String.format(
                "%s already have an appointment scheduled at %s on %s",
                professionalName,
                appointmentDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                appointmentDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                )
        );
    }


}
