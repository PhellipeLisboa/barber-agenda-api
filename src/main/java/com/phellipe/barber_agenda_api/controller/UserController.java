package com.phellipe.barber_agenda_api.controller;

import com.phellipe.barber_agenda_api.dto.UserResponseDto;
import com.phellipe.barber_agenda_api.infra.config.ApiPaths;
import com.phellipe.barber_agenda_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/users")
@SecurityRequirement(name = "bearerAuth")
@ApiResponse(responseCode = "401", description = "Ação não autorizada.")
@ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
@Tag(name = "Users", description = "Controlador para administrar usuários.")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @Operation(operationId = "01-listUsers", summary = "Listar usuários", description = "Método para listar todos os usuários.")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{user_id}")
    @PreAuthorize("@UserSecurity.canManageUser(#user_id)")
    @Operation(operationId = "02-getUserById", summary = "Buscar um usuário", description = "Método para buscar um usuário.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID user_id) {
        return ResponseEntity.ok(userService.findById(user_id));
    }

    @DeleteMapping("/{user_id}")
    @PreAuthorize("@UserSecurity.canManageUser(#user_id)")
    @Operation(operationId = "03-deleteUserById", summary = "Deletar um usuário", description = "Método para deletar um usuário.")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<Void> delete(@PathVariable UUID user_id) {
        userService.delete(user_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/giveProfessionalRole/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @Operation(operationId = "04-giveProfessionalRole", summary = "Conceder a role PROFESSIONAL a um usuário", description = "Método para conceder a role PROFESSIONAL a um usuário.")
    @ApiResponse(responseCode = "200", description = "Role PROFESSIONAL concedida com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "409", description = "Usuário já possui esta role.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<Void> giveProfessionalRole(@PathVariable UUID user_id) {
        userService.giveProfessionalRole(user_id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/revokeProfessionalRole/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @Operation(operationId = "04-revokeProfessionalRole", summary = "Revogar a role PROFESSIONAL de um usuário", description = "Método para revogar a role PROFESSIONAL de um usuário.")
    @ApiResponse(responseCode = "200", description = "Role PROFESSIONAL revogada com sucesso.")
    @ApiResponse(responseCode = "400", description = "Usuário não possui a role PROFESSIONAL")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "409", description = "Usuário já possui esta role.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<Void> revokeProfessionalRole(@PathVariable UUID user_id) {
        userService.revokeProfessionalRole(user_id);
        return ResponseEntity.ok().build();
    }


}
