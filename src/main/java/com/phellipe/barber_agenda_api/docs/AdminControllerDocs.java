package com.phellipe.barber_agenda_api.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@ApiResponse(responseCode = "401", description = "Ação não autorizada.")
@ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
@Tag(name = "Admin", description = "Endpoints para ações de administradores do sistema.")
public interface AdminControllerDocs {

    @Operation(summary = "Conceder permissão", description = "Conceder permissão a um usuário específico.")
    @ApiResponse(responseCode = "200", description = "Permissão concedida com sucesso.")
    @ApiResponse(responseCode = "400", description = "Role inválida")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "409", description = "Usuário já possui esta role.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<Void> givePermission(
            @Parameter(description = "ID do usuário para conceder permissões.")
            @PathVariable UUID user_id,
            @Parameter(description = "Role a ser concedida ao usuário.")
            @PathVariable String role
    );

    @Operation(summary = "Revogar permissão", description = "Revogar permissão de um usuário específico.")
    @ApiResponse(responseCode = "200", description = "Permissão revogada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "400", description = """
            Role inválida.
            
            Usuário não possui a role que está sendo revogada.
            
            Não é possível revogar a role USER de um usuário.
            """)
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<Void> revokePermission(
            @Parameter(description = "ID do usuário para revogar permissões.")
            @PathVariable UUID user_id,
            @Parameter(description = "Role a ser revogada do usuário.")
            @PathVariable String role
    );

}
