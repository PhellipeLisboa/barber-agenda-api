package com.phellipe.app_de_agendamento.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String email) {
        super(String.format("A user with email %s already exists.", email));
    }

}
