package com.phellipe.barber_agenda_api.controller;

import com.phellipe.barber_agenda_api.docs.AuthControllerDocs;
import com.phellipe.barber_agenda_api.dto.auth.AuthResponseDto;
import com.phellipe.barber_agenda_api.dto.auth.LoginRequestDto;
import com.phellipe.barber_agenda_api.dto.auth.RegisterRequestDto;
import com.phellipe.barber_agenda_api.infra.config.ApiPaths;
import com.phellipe.barber_agenda_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/auth")
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok().body(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok().body(authService.register(dto));
    }

}
