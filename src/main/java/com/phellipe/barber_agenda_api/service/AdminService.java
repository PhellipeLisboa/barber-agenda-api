package com.phellipe.barber_agenda_api.service;

import com.phellipe.barber_agenda_api.dto.UserResponseDto;
import com.phellipe.barber_agenda_api.exception.*;
import com.phellipe.barber_agenda_api.mapper.UserMapper;
import com.phellipe.barber_agenda_api.model.user.Role;
import com.phellipe.barber_agenda_api.model.user.User;
import com.phellipe.barber_agenda_api.repository.RoleRepository;
import com.phellipe.barber_agenda_api.repository.UserRepository;
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


    public void givePermission(UUID userId, String roleName) {


        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Set<Role> userRoles =  user.getRoles();

        Role role = roleRepository.findByRole(roleName).orElseThrow(
                () -> new InvalidRoleException(roleName)
        );

        if (userRoles.contains(role)) {
            throw new UserAlreadyHasRoleException(role.getRole());
        }

        userRoles.add(role);
        user.setRoles(userRoles);
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
