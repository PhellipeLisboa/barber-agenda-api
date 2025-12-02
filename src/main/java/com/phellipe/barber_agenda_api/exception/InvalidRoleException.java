package com.phellipe.barber_agenda_api.exception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String roleName) {
        super(String.format("%s is not a valid role name.", roleName));
    }
}
