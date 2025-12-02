package com.phellipe.barber_agenda_api.controller;

import com.phellipe.barber_agenda_api.dto.auth.LoginRequestDto;
import com.phellipe.barber_agenda_api.dto.auth.AuthResponseDto;
import com.phellipe.barber_agenda_api.dto.auth.RegisterRequestDto;
import com.phellipe.barber_agenda_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Controlador para realizar autenticação e criar novos usuários.")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(operationId = "01-login", summary = "Fazer login", description = "Método para fazer a autenticão de um usuário.")
    @ApiResponse(responseCode = "200", description = "Logado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok().body(authService.login(dto));
    }

    @PostMapping("/register")
    @Operation(operationId = "02-register",summary = "Cadastrar usuário", description = "Método para registrar um novo usuário.")
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso.")
    @ApiResponse(responseCode = "409", description = "O email já foi utilizado.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok().body(authService.register(dto));
    }

}
