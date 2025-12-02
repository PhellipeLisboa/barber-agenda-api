package com.phellipe.barber_agenda_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

        @NotBlank(message = "Name is required.")
        @Size(min = 4, max = 30, message = "Name must be between 4 and 30 characters.")
        String name,
        @NotBlank(message = "Email is required.")
        String email,
        @NotBlank(message = "Password is required.")
        String password
) {
}
