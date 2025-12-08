package com.phellipe.barber_agenda_api.controller;

import com.phellipe.barber_agenda_api.docs.UserControllerDocs;
import com.phellipe.barber_agenda_api.dto.UserResponseDto;
import com.phellipe.barber_agenda_api.infra.config.ApiPaths;
import com.phellipe.barber_agenda_api.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{user_id}")
    @PreAuthorize("@UserSecurity.canManageUser(#user_id)")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID user_id) {
        return ResponseEntity.ok(userService.findById(user_id));
    }

    @DeleteMapping("/{user_id}")
    @PreAuthorize("@UserSecurity.canManageUser(#user_id)")
    public ResponseEntity<Void> delete(@PathVariable UUID user_id) {
        userService.delete(user_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/giveProfessionalRole/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Void> giveProfessionalRole(@PathVariable UUID user_id) {
        userService.giveProfessionalRole(user_id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/revokeProfessionalRole/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Void> revokeProfessionalRole(@PathVariable UUID user_id) {
        userService.revokeProfessionalRole(user_id);
        return ResponseEntity.ok().build();
    }


}
