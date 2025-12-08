package com.phellipe.barber_agenda_api.controller;

import com.phellipe.barber_agenda_api.docs.AdminControllerDocs;
import com.phellipe.barber_agenda_api.infra.config.ApiPaths;
import com.phellipe.barber_agenda_api.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/admin")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController implements AdminControllerDocs {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/users/{user_id}/givePermission/{role}")
    public ResponseEntity<Void> givePermission(@PathVariable UUID user_id, @PathVariable String role) {
        adminService.givePermission(user_id, role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{user_id}/revokePermission/{role}")
    public ResponseEntity<Void> revokePermission(@PathVariable UUID user_id, @PathVariable String role) {
        adminService.revokePermission(user_id, role);
        return ResponseEntity.ok().build();
    }

}
