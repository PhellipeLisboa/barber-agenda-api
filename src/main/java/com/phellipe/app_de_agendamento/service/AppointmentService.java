package com.phellipe.app_de_agendamento.service;

import com.phellipe.app_de_agendamento.dto.appointment.AppointmentRequestDto;
import com.phellipe.app_de_agendamento.dto.appointment.AppointmentResponseDto;
import com.phellipe.app_de_agendamento.dto.appointment.AppointmentPatchDto;
import com.phellipe.app_de_agendamento.exception.*;
import com.phellipe.app_de_agendamento.mapper.AppointmentMapper;
import com.phellipe.app_de_agendamento.model.Agenda;
import com.phellipe.app_de_agendamento.model.Appointment;
import com.phellipe.app_de_agendamento.model.user.User;
import com.phellipe.app_de_agendamento.repository.AgendaRepository;
import com.phellipe.app_de_agendamento.repository.AppointmentRepository;
import com.phellipe.app_de_agendamento.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public AppointmentService(AppointmentRepository appointmentRepository, AgendaRepository agendaRepository, UserRepository userRepository, AuthService authService) {
        this.appointmentRepository = appointmentRepository;
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public List<AppointmentResponseDto> findAll(Long agendaId) {

        agendaRepository.findById(agendaId).orElseThrow(
                () -> new ResourceNotFoundException("agenda", agendaId)
        );

        return appointmentRepository.findAllByAgendaId(agendaId)
                .stream()
                .map(AppointmentMapper::toDto)
                .toList();

    }

    public AppointmentResponseDto findById(Long agendaId, Long id) {

        agendaRepository.findById(agendaId).orElseThrow(
                () -> new ResourceNotFoundException("agenda", agendaId)
        );

        Appointment appointment = appointmentRepository.findByAgendaIdAndId(agendaId, id).orElseThrow(
                () -> new ResourceNotFoundException("appointment", id, "agenda", agendaId)
        );

        return AppointmentMapper.toDto(appointment);

    }

    public AppointmentResponseDto save(Long agendaId, AppointmentRequestDto dto) {

        Agenda agenda = agendaRepository.findById(agendaId).orElseThrow(
                () -> new ResourceNotFoundException("agenda", agendaId)
        );

        Appointment appointment = AppointmentMapper.toEntity(dto);

        User professional = userRepository.findById(dto.professionalId()).orElseThrow(
                () -> new UserNotFoundException(dto.professionalId())
        );

        User customer = authService.getAuthenticatedUser();
        if (professional.hasRole("PROFESSIONAL")) {
            appointment.setProfessionalName(professional.getName());
        } else {
            throw new RequiredRoleException("PROFESSIONAL");
        }

        appointment.setAgenda(agenda);
        appointment.setCustomerName(customer.getName());

        validateAppointmentDateTime(dto.appointmentDateTime(), appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return AppointmentMapper.toDto(savedAppointment);
    }

    public AppointmentResponseDto update(Long agendaId, Long id, AppointmentPatchDto dto) {

        agendaRepository.findById(agendaId).orElseThrow(
                () -> new ResourceNotFoundException("agenda", agendaId)
        );

        Appointment appointmentEntity = appointmentRepository.findByAgendaIdAndId(agendaId, id).orElseThrow(
                () -> new ResourceNotFoundException("appointment", id, "agenda", agendaId)
        );

        if (dto.professionalId().isPresent()) {
            User professional = userRepository.findById(dto.professionalId().get()).orElseThrow(
                    () -> new UserNotFoundException(dto.professionalId().get())
            );
            if (professional.hasRole("PROFESSIONAL")) {
                appointmentEntity.setProfessionalId(professional.getId());
                appointmentEntity.setProfessionalName(professional.getName());
            } else {
                throw new RequiredRoleException("PROFESSIONAL");
            }
        }

        if (dto.appointmentDateTime().isPresent()) {
            validateAppointmentDateTime(dto.appointmentDateTime().get(), appointmentEntity);
        }

        dto.appointmentDateTime().ifPresent(appointmentEntity::setAppointmentDateTime);

        Appointment updatedAppointment = appointmentRepository.save(appointmentEntity);

        return AppointmentMapper.toDto(updatedAppointment);
    }

    public void delete(Long agendaId, Long id) {

        agendaRepository.findById(agendaId).orElseThrow(
                () -> new ResourceNotFoundException("agenda", agendaId)
        );

        if (!appointmentRepository.existsByAgendaIdAndId(agendaId, id)) {
            throw new ResourceNotFoundException("appointment", id, "agenda", agendaId);
        }

        appointmentRepository.deleteById(id);

    }


    private void validateAppointmentDateTime(LocalDateTime appointmentDateTime, Appointment appointment) {

        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentDateTimeException();
        }

        LocalTime hour = appointmentDateTime.toLocalTime();
        LocalTime start = appointment.getAgenda().getWorkdayStart();
        LocalTime end = appointment.getAgenda().getWorkdayEnd();

        if (hour.isBefore(start) || hour.equals(end) || hour.isAfter(end)) {
            throw new AppointmentOutOfWorkdayException(start, end);
        }

        if (appointmentRepository.existsByAgendaIdAndAppointmentDateTime(
                appointment.getAgenda().getId(),
                appointmentDateTime)) {
            throw new AppointmentDateTimeAlreadyBookedException(appointmentDateTime);
        }
    }

}
