package com.phellipe.app_de_agendamento.exception;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentOutOfWorkdayException extends RuntimeException{

    public AppointmentOutOfWorkdayException(LocalTime start, LocalTime end) {
        super(String.format("Appointments cannot be scheduled outside the workday hours (%s - %s)",
                        start.format(DateTimeFormatter.ofPattern("HH:mm")),
                        end.format(DateTimeFormatter.ofPattern("HH:mm"))
                ));
    }

}
