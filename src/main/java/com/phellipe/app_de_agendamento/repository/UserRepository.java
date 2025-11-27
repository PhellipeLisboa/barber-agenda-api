package com.phellipe.app_de_agendamento.repository;

import com.phellipe.app_de_agendamento.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID userId);
}
