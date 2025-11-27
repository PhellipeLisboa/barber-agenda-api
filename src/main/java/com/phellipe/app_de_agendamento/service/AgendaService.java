package com.phellipe.app_de_agendamento.service;

import com.phellipe.app_de_agendamento.dto.AddProfessionalDto;
import com.phellipe.app_de_agendamento.dto.UserResponseDto;
import com.phellipe.app_de_agendamento.dto.agenda.AgendaPatchDto;
import com.phellipe.app_de_agendamento.dto.agenda.AgendaRequestDto;
import com.phellipe.app_de_agendamento.dto.agenda.AgendaResponseDto;
import com.phellipe.app_de_agendamento.exception.RequiredRoleException;
import com.phellipe.app_de_agendamento.exception.InvalidWorkdayRangeException;
import com.phellipe.app_de_agendamento.exception.ResourceNotFoundException;
import com.phellipe.app_de_agendamento.exception.UserNotFoundException;
import com.phellipe.app_de_agendamento.mapper.AgendaMapper;
import com.phellipe.app_de_agendamento.mapper.UserMapper;
import com.phellipe.app_de_agendamento.model.Agenda;
import com.phellipe.app_de_agendamento.model.user.User;
import com.phellipe.app_de_agendamento.repository.AgendaRepository;
import com.phellipe.app_de_agendamento.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public AgendaService(AgendaRepository agendaRepository, AuthService authService, UserRepository userRepository) {
        this.agendaRepository = agendaRepository;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    public List<AgendaResponseDto> findAll() {
        return agendaRepository.findAll().stream()
                .map(AgendaMapper::toDto)
                .toList();
    }

    public AgendaResponseDto findById(Long agendaId) {
        Agenda agenda = getAgenda(agendaId);

        return AgendaMapper.toDto(agenda);
    }

    public Set<UserResponseDto> findProfessionals(Long agendaId) {
        Agenda agenda = getAgenda(agendaId);

        return agenda.getProfessionals().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toSet());

    }

    public UserResponseDto findProfessionalById(Long agendaId, UUID professionalId) {
        Agenda agenda = getAgenda(agendaId);

        User professional = userRepository.findById(professionalId).orElseThrow(
                () -> new UserNotFoundException(professionalId)
        );;

        if (agenda.getProfessionals().contains(professional)) {
            return UserMapper.toDto(professional);
        } else {
            throw new ResourceNotFoundException("Professional", professionalId, "agenda", agendaId);
        }
    }

    public AgendaResponseDto save(AgendaRequestDto dto) {

        validateWorkdayRange(dto.workdayStart(), dto.workdayEnd());

        Agenda agenda = AgendaMapper.toEntity(dto);

        agenda.setOwner(authService.getAuthenticatedUser());

        Agenda savedAgenda = agendaRepository.save(agenda);

        return AgendaMapper.toDto(savedAgenda);
    }

    public AgendaResponseDto update(Long agendaId, AgendaPatchDto dto) {

        Agenda agendaEntity = getAgenda(agendaId);

        LocalTime start = dto.workdayStart().orElse(agendaEntity.getWorkdayStart());
        LocalTime end = dto.workdayEnd().orElse(agendaEntity.getWorkdayEnd());

        validateWorkdayRange(start, end);

        dto.name().ifPresent(agendaEntity::setName);
        agendaEntity.setWorkdayStart(start);
        agendaEntity.setWorkdayEnd(end);

        Agenda updatedAgenda = agendaRepository.save(agendaEntity);

        return AgendaMapper.toDto(updatedAgenda);

    }

    public void delete(Long agendaId) {

        if (!agendaRepository.existsById(agendaId)) {
            throw new ResourceNotFoundException("agenda", agendaId);
        }

        agendaRepository.deleteById(agendaId);
    }


    // TODO: Implementar métodos para gerenciar profissionais
    public List<UserResponseDto> getProfessionals(Long agendaId) {

        Agenda agenda = getAgenda(agendaId);

        Set<User> professionals = agenda.getProfessionals();

        return professionals.stream()
                .map(UserMapper::toDto)
                .toList();

    }

    public UserResponseDto getProfessionalById(Long agendaId, UUID professionalId) {

        Agenda agenda = getAgenda(agendaId);

        User professional = agenda.getProfessionals().stream()
                .filter(agendaProfessional -> agendaProfessional.getId().equals(professionalId))
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException("professional", professionalId, "agenda", agendaId)
                );

        return UserMapper.toDto(professional);

    }

    public UserResponseDto addProfessional(Long agendaId, AddProfessionalDto dto) {

        Agenda agenda = getAgenda(agendaId);

        User professional = userRepository.findById(dto.professionalId()).orElseThrow(
                () -> new UserNotFoundException(dto.professionalId())
        );

        if (!professional.hasRole("PROFESSIONAL")) {
            throw new RequiredRoleException("PROFESSIONAL");
        }

        agenda.getProfessionals().add(professional);

        agendaRepository.save(agenda);

        return UserMapper.toDto(professional);
    }

    @Transactional
    public void removeProfessional(Long agendaId, UUID professionalId) {

        Agenda agenda = getAgenda(agendaId);

        User professional = agenda.getProfessionals().stream()
                .filter(agendaProfessional -> agendaProfessional.getId().equals(professionalId))
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException("professional", professionalId, "agenda", agendaId)
                );

        agenda.getProfessionals().remove(professional);

        agendaRepository.save(agenda);

    }

    private void validateWorkdayRange(LocalTime workdayStart, LocalTime workdayEnd) {
        if (workdayStart.isAfter(workdayEnd)) throw new InvalidWorkdayRangeException();
    }

    private Agenda getAgenda(Long agendaId) {
        return agendaRepository.findById(agendaId).orElseThrow(
                () -> new ResourceNotFoundException("agenda", agendaId)
        );
    }

}
