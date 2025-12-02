package com.phellipe.barber_agenda_api.exception;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentOutOfBusinessHourException extends RuntimeException{

    public AppointmentOutOfBusinessHourException(LocalTime start, LocalTime end) {
        super(String.format("Appointments cannot be scheduled outside the workday hours (%s - %s)",
                        start.format(DateTimeFormatter.ofPattern("HH:mm")),
                        end.format(DateTimeFormatter.ofPattern("HH:mm"))
                ));
    }

}
