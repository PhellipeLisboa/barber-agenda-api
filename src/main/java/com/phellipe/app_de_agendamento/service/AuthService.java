package com.phellipe.app_de_agendamento.service;

import com.phellipe.app_de_agendamento.dto.auth.LoginRequestDto;
import com.phellipe.app_de_agendamento.dto.auth.AuthResponseDto;
import com.phellipe.app_de_agendamento.dto.auth.RegisterRequestDto;
import com.phellipe.app_de_agendamento.exception.InvalidCredentialsException;
import com.phellipe.app_de_agendamento.exception.ResourceNotFoundException;
import com.phellipe.app_de_agendamento.exception.UserAlreadyExistsException;
import com.phellipe.app_de_agendamento.exception.UserNotFoundException;
import com.phellipe.app_de_agendamento.infra.security.TokenService;
import com.phellipe.app_de_agendamento.model.user.Role;
import com.phellipe.app_de_agendamento.model.user.User;
import com.phellipe.app_de_agendamento.repository.RoleRepository;
import com.phellipe.app_de_agendamento.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.email()).orElseThrow(
                () -> new InvalidCredentialsException()
        );

        if (passwordEncoder.matches(dto.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return new AuthResponseDto(user.getName(), token);
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public AuthResponseDto register(RegisterRequestDto dto) {

        Optional<User> user = userRepository.findByEmail(dto.email());

        if (user.isEmpty()) {

            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(dto.password()));
            newUser.setEmail(dto.email());
            newUser.setName(dto.name());

            Role defaultRole = roleRepository.findByRole("USER").orElseThrow(
                    () -> new ResourceNotFoundException("ROLE_USER")
            );

            newUser.getRoles().add(defaultRole);

            userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return new AuthResponseDto(newUser.getName(), token);

        }

        throw new UserAlreadyExistsException(user.get().getEmail());

    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
