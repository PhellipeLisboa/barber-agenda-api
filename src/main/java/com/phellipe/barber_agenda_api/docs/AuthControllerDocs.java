package com.phellipe.barber_agenda_api.docs;


import com.phellipe.barber_agenda_api.dto.auth.AuthResponseDto;
import com.phellipe.barber_agenda_api.dto.auth.LoginRequestDto;
import com.phellipe.barber_agenda_api.dto.auth.RegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Endpoints para realizar autenticação criação de usuários.")
public interface AuthControllerDocs {

    @Operation(summary = "Fazer login", description = "Realizar a autenticão de um usuário.")
    @ApiResponse(responseCode = "200", description = "Logado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    ResponseEntity<AuthResponseDto> login(
            @Parameter(description = "Credenciais do usuário para login.")
            @RequestBody LoginRequestDto dto
    );

    @Operation(summary = "Cadastrar usuário", description = "Registrar um novo usuário.")
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso.")
    @ApiResponse(responseCode = "409", description = "O email já foi utilizado.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    ResponseEntity<AuthResponseDto> register(
            @Parameter(description = "Dados do novo usuário para registro.")
            @RequestBody RegisterRequestDto dto
    );

}
