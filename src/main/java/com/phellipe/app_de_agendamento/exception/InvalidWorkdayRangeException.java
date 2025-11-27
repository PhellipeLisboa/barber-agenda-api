package com.phellipe.app_de_agendamento.exception;

public class InvalidWorkdayRangeException extends RuntimeException {

    public InvalidWorkdayRangeException() {
        super("Closing time cannot be before the opening time.");
    }

}
