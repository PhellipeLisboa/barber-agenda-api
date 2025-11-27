package com.phellipe.app_de_agendamento.repository;

import com.phellipe.app_de_agendamento.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
