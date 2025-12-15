package com.phellipe.barber_agenda_api.service;

import com.phellipe.barber_agenda_api.dto.UserResponseDto;
import com.phellipe.barber_agenda_api.exception.*;
import com.phellipe.barber_agenda_api.mapper.UserMapper;
import com.phellipe.barber_agenda_api.model.user.Role;
import com.phellipe.barber_agenda_api.model.user.User;
import com.phellipe.barber_agenda_api.repository.RoleRepository;
import com.phellipe.barber_agenda_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public UserResponseDto findById(UUID Id) {
        User user = userRepository.findById(Id).orElseThrow(
                () -> new UserNotFoundException(Id)
        );

        return UserMapper.toDto(user);
    }

    @Transactional
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void giveProfessionalRole(UUID userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Set<Role> userRoles =  user.getRoles();

        Role role = roleRepository.findByRole("PROFESSIONAL").get();

        if (userRoles.contains(role)) {
            throw new UserAlreadyHasRoleException("PROFESSIONAL");
        }

        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);

    }

    @Transactional
    public void revokeProfessionalRole(UUID userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Set<Role> userRoles =  user.getRoles();

        Role role = roleRepository.findByRole("PROFESSIONAL").get();

        if (!userRoles.contains(role)) {
            throw new RequiredRoleException(role.getRole());
        }

        userRoles.remove(role);
        user.setRoles(userRoles);
        userRepository.save(user);

    }

}
