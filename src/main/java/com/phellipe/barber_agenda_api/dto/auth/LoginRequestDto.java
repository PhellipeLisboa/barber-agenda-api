package com.phellipe.barber_agenda_api.dto.auth;

public record LoginRequestDto(
        String email,
        String password
) {
}
