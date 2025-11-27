package com.phellipe.app_de_agendamento.exception;

public class UserAlreadyHasRoleException extends RuntimeException {
    public UserAlreadyHasRoleException(String roleName) {
        super(String.format("User already have the role %s", roleName));
    }
}
