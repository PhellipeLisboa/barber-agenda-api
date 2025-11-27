package com.phellipe.app_de_agendamento.dto.auth;

import com.phellipe.app_de_agendamento.model.user.Role;

import java.util.Set;

public record RegisterRequestDto(
        String name,
        String email,
        String password
) {
}
