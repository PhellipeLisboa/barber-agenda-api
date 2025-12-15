package com.phellipe.barber_agenda_api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank(message = "User's email is required.")
        @Email(message = "Email format is invalid.", regexp = "^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$")
        @Schema(description = "User's email for authentication.", example = "user@example.com")
        String email,

        @NotBlank(message = "User's password is required.")
        @Size(min = 6, message = "Password size must be at least 6 characters.")
        @Schema(description = "User's password for authentication. Must be at least 6 characters long.", example = "123456")
        String password
) {
}
