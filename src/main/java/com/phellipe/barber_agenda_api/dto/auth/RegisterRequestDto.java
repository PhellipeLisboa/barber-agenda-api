package com.phellipe.barber_agenda_api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

        @NotBlank(message = "Name is required.")
        @Size(min = 4, max = 30, message = "Name must be between 4 and 30 characters.")
        @Schema(description = "User's name.", example = "John Doe")
        String name,
        @NotBlank(message = "Email is required.")
        @Email(message = "Email format is invalid.", regexp = "^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$")
        @Schema(description = "User's email address.", example = "user@example.com")
        String email,
        @NotBlank(message = "Password is required.")
        @Size(min = 6, message = "Password size must be at least 6 characters.")
        @Schema(description = "User's password. Must be at least 6 characters long.", example = "123456")
        String password
) {
}
