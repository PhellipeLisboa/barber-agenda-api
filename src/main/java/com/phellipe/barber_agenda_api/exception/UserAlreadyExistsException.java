package com.phellipe.barber_agenda_api.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String email) {
        super(String.format("A user with email %s already exists.", email));
    }

}
