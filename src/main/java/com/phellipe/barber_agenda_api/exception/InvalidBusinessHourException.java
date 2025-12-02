package com.phellipe.barber_agenda_api.exception;

public class InvalidBusinessHourException extends RuntimeException {

    public InvalidBusinessHourException() {
        super("Closing time cannot be before the opening time.");
    }

}
