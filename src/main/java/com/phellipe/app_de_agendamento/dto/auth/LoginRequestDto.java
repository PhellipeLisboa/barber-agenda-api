package com.phellipe.app_de_agendamento.dto.auth;

public record LoginRequestDto(
        String email,
        String password
) {
}
