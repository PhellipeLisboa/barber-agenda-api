package com.phellipe.barber_agenda_api.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super(String.format("User with email %s not found.", email));
    }
    public UserNotFoundException(UUID id) {
        super(String.format("User with id %s not found.", id));
    }

}
