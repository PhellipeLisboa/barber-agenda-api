package com.phellipe.barber_agenda_api.service;

import com.phellipe.barber_agenda_api.dto.auth.LoginRequestDto;
import com.phellipe.barber_agenda_api.dto.auth.AuthResponseDto;
import com.phellipe.barber_agenda_api.dto.auth.RegisterRequestDto;
import com.phellipe.barber_agenda_api.exception.InvalidCredentialsException;
import com.phellipe.barber_agenda_api.exception.ResourceNotFoundException;
import com.phellipe.barber_agenda_api.exception.UserAlreadyExistsException;
import com.phellipe.barber_agenda_api.infra.security.TokenService;
import com.phellipe.barber_agenda_api.model.user.Role;
import com.phellipe.barber_agenda_api.model.user.User;
import com.phellipe.barber_agenda_api.repository.RoleRepository;
import com.phellipe.barber_agenda_api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
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
