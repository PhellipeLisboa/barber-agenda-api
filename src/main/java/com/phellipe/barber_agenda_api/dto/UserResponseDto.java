package com.phellipe.barber_agenda_api.dto;

import java.util.UUID;

public record UserResponseDto(
        String name,
        String email,
        UUID id,
        String role
) {
}
