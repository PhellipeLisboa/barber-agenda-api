package com.phellipe.app_de_agendamento.service;

import com.phellipe.app_de_agendamento.dto.UserResponseDto;
import com.phellipe.app_de_agendamento.exception.*;
import com.phellipe.app_de_agendamento.mapper.UserMapper;
import com.phellipe.app_de_agendamento.model.user.Role;
import com.phellipe.app_de_agendamento.model.user.User;
import com.phellipe.app_de_agendamento.repository.RoleRepository;
import com.phellipe.app_de_agendamento.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public void givePermission(UUID userId, String roleName) {


        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Set<Role> roles =  user.getRoles();

        Role role = roleRepository.findByRole(roleName).orElseThrow(
                () -> new InvalidRoleException(roleName)
        );

        if (roles.contains(role)) {
            throw new UserAlreadyHasRoleException(role.getRole());
        }

        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

    }

    public void revokePermission(UUID userId, String roleName) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Set<Role> roles =  user.getRoles();

        Role role = roleRepository.findByRole(roleName).orElseThrow(
                () -> new InvalidRoleException(roleName)
        );

        if (role.getRole().equals("USER")) {
            throw new InvalidOperationException("It's not possible to revoke the role USER, delete user's account instead.");
        }

        if (!roles.contains(role)) {
            throw new RequiredRoleException(role.getRole());
        }

        roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);

    }
}
