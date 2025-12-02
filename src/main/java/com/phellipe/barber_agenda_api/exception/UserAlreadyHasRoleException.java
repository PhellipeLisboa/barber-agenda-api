package com.phellipe.barber_agenda_api.exception;

public class UserAlreadyHasRoleException extends RuntimeException {
    public UserAlreadyHasRoleException(String roleName) {
        super(String.format("User already have the role %s", roleName));
    }
}
