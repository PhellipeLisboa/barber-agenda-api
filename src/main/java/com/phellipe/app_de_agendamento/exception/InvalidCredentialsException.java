package com.phellipe.app_de_agendamento.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }

}
