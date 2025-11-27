package com.phellipe.app_de_agendamento.dto;

import java.util.UUID;

public record UserResponseDto(
        String name,
        String email,
        UUID id,
        String role
) {
}
