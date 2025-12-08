package com.phellipe.barber_agenda_api.docs;

import com.phellipe.barber_agenda_api.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@ApiResponse(responseCode = "401", description = "Ação não autorizada.")
@ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
@Tag(name = "Users", description = "Endpoints para administrar usuários.")
public interface UserControllerDocs {

    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários cadastrados.")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<List<UserResponseDto>> findAll();

    @Operation(summary = "Buscar um usuário", description = "Busca um usuário pelo ID.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<UserResponseDto> findById(
            @Parameter(description = "ID do usuário para realizar a busca.")
            @PathVariable UUID user_id
    );

    @Operation(summary = "Deletar um usuário", description = "Remove um usuário pelo ID.")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID do usuário para deletar.")
            @PathVariable UUID user_id
    );

    @Operation(summary = "Conceder a role PROFESSIONAL", description = "Concede a role PROFESSIONAL a um usuário.")
    @ApiResponse(responseCode = "200", description = "Role PROFESSIONAL concedida com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "409", description = "Usuário já possui esta role.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<Void> giveProfessionalRole(
            @Parameter(description = "ID do usuário para conceder a role PROFESSIONAL.")
            @PathVariable UUID user_id
    );

    @Operation(summary = "Revogar a role PROFESSIONAL", description = "Revoga a role PROFESSIONAL de um usuário.")
    @ApiResponse(responseCode = "200", description = "Role PROFESSIONAL revogada com sucesso.")
    @ApiResponse(responseCode = "400", description = "Usuário não possui a role PROFESSIONAL")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<Void> revokeProfessionalRole(
            @Parameter(description = "ID do usuário para revogar a role PROFESSIONAL.")
            @PathVariable UUID user_id
    );

}
