package com.phellipe.app_de_agendamento.controller;

import com.phellipe.app_de_agendamento.dto.UserResponseDto;
import com.phellipe.app_de_agendamento.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
@ApiResponse(responseCode = "401", description = "Ação não autorizada.")
@ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
@Tag(name = "Admin", description = "Controlador para administradores")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    @Operation(operationId = "01-givePermission", summary = "Listar usuários", description = "Método para listar todos os usuários.")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<List<UserResponseDto>> findUsers() {
        return ResponseEntity.ok(adminService.findAllUsers());
    }

    @PostMapping("/users/{user_id}/givePermission/{role}")
    @Operation(operationId = "02-givePermission", summary = "Conceder permissão", description = "Método para conceder permissão a um usuário específico.")
    @ApiResponse(responseCode = "200", description = "Permissão concedida com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "400", description = "Role inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<Void> givePermission(@PathVariable UUID user_id, @PathVariable String role) {
        adminService.givePermission(user_id, role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{user_id}/revokePermission/{role}")
    @Operation(operationId = "03-revokePermission", summary = "Revogar permissão", description = "Método para revogar permissão de um usuário específico.")
    @ApiResponse(responseCode = "200", description = "Permissão revogada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "400", description = "Role inválida")
    @ApiResponse(responseCode = "400", description = "Não é possível revogar a role USER de um usuário")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<Void> revokePermission(@PathVariable UUID user_id, @PathVariable String role) {
        adminService.revokePermission(user_id, role);
        return ResponseEntity.ok().build();
    }

}
