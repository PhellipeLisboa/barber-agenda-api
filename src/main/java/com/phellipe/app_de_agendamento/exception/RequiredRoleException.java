package com.phellipe.app_de_agendamento.exception;

public class RequiredRoleException extends RuntimeException {
    public RequiredRoleException(String role) {
        super(String.format("Only users with role %s can be chosen.", role));
    }

}
