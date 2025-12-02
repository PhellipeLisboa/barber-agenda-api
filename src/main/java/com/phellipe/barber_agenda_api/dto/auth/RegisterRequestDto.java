package com.phellipe.barber_agenda_api.dto.auth;

public record RegisterRequestDto(
        String name,
        String email,
        String password
) {
}
