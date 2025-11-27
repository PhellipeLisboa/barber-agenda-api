package com.phellipe.app_de_agendamento.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDateTimeAlreadyBookedException extends RuntimeException{

    public AppointmentDateTimeAlreadyBookedException(LocalDateTime appointmentDateTime) {

        super(String.format(
                "An appointment already exists at %s on %s",
                appointmentDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                appointmentDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                )
        );
    }


}
