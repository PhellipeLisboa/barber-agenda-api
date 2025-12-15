package com.phellipe.barber_agenda_api.service;

import com.phellipe.barber_agenda_api.dto.appointment.AppointmentRequestDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentResponseDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentPatchDto;
import com.phellipe.barber_agenda_api.exception.*;
import com.phellipe.barber_agenda_api.mapper.AppointmentMapper;
import com.phellipe.barber_agenda_api.model.Appointment;
import com.phellipe.barber_agenda_api.model.BusinessHour;
import com.phellipe.barber_agenda_api.model.user.User;
import com.phellipe.barber_agenda_api.repository.AppointmentRepository;
import com.phellipe.barber_agenda_api.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BusinessHourService businessHourService;
    private final UserRepository userRepository;
    private final AuthService authService;

    public AppointmentService(AppointmentRepository appointmentRepository, BusinessHourService businessHourService, UserRepository userRepository, AuthService authService) {
        this.appointmentRepository = appointmentRepository;
        this.businessHourService = businessHourService;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public List<AppointmentResponseDto> findAll() {

        User user = authService.getAuthenticatedUser();

        if (user.isOnlyUser()) {
            return appointmentRepository.findByCustomerId(user.getId())
                    .stream()
                    .filter(appointment -> appointment.getCustomerId().equals(user.getId()))
                    .map(AppointmentMapper::toDto)
                    .toList();
        }

        if (user.hasRole("PROFESSIONAL")) {
            return appointmentRepository.findByProfessionalId(user.getId())
                    .stream()
                    .filter(appointment -> appointment.getProfessionalId().equals(user.getId()))
                    .map(AppointmentMapper::toDto)
                    .toList();
        }

        return appointmentRepository.findAll()
                .stream()
                .map(AppointmentMapper::toDto)
                .toList();

    }

    public AppointmentResponseDto findById(Long id) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("appointment", id)
        );

        return AppointmentMapper.toDto(appointment);

    }

    @Transactional
    public AppointmentResponseDto save(AppointmentRequestDto dto) {

        Appointment appointment = AppointmentMapper.toEntity(dto);

        User professional = userRepository.findById(dto.professionalId()).orElseThrow(
                () -> new UserNotFoundException(dto.professionalId())
        );

        if (professional.hasRole("PROFESSIONAL")) {
            appointment.setProfessionalName(professional.getName());
        } else {
            throw new RequiredRoleException("PROFESSIONAL");
        }

        User authenticatedUser = authService.getAuthenticatedUser();
        User customer = userRepository.findById(dto.customerId()).orElseThrow(
                () -> new UserNotFoundException(dto.customerId())
        );

        boolean isSelfAppointment = authenticatedUser.getId().equals(customer.getId());
        boolean canScheduleForOthers = authenticatedUser.hasRole("ADMIN") || authenticatedUser.hasRole("OWNER");

        if (isSelfAppointment || canScheduleForOthers) {
            appointment.setCustomerName(customer.getName());
        } else {
            throw new AccessDeniedException("You do not have permission to schedule an appointment for another user.");
        }

        validateAppointmentDateTime(dto.appointmentDateTime(), appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return AppointmentMapper.toDto(savedAppointment);
    }

    @Transactional
    public AppointmentResponseDto update(Long id, AppointmentPatchDto dto) {


        Appointment appointmentEntity = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("appointment", id)
        );

        LocalDateTime effectiveDateTime = dto.appointmentDateTime().orElse(appointmentEntity.getAppointmentDateTime());

        LocalDateTime limit = LocalDateTime.now().plusHours(24);

        if (effectiveDateTime.isBefore(limit)) {
            throw new InvalidOperationException("Appointments can only be updated or deleted at least 24 hours in advance.");
        }

        dto.professionalId().ifPresent(professionalId -> {
            User professional = userRepository.findById(professionalId).orElseThrow(
                    () -> new UserNotFoundException(professionalId)
            );

            if (!professional.hasRole("PROFESSIONAL")) {
                throw new RequiredRoleException("PROFESSIONAL");
            }

            appointmentEntity.setProfessionalId(professional.getId());
            appointmentEntity.setProfessionalName(professional.getName());

        });

        dto.appointmentDateTime().ifPresent(newDateTime -> {
            validateAppointmentDateTime(newDateTime, appointmentEntity);
            appointmentEntity.setAppointmentDateTime(newDateTime);
        });

        Appointment updatedAppointment = appointmentRepository.save(appointmentEntity);

        return AppointmentMapper.toDto(updatedAppointment);
    }

    @Transactional
    public void delete(Long id) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("appointment", id)
        );

        LocalDateTime limit = LocalDateTime.now().plusHours(24);

        if (appointment.getAppointmentDateTime().isBefore(limit)) {
            throw new InvalidOperationException("Appointments can only be updated or deleted at least 24 hours in advance.");
        }

        appointmentRepository.delete(appointment);

    }


    private void validateAppointmentDateTime(LocalDateTime appointmentDateTime, Appointment appointment) {

        validateAppointmentInPast(appointmentDateTime);

        validateAppointmentOutOfBusinessHour(appointmentDateTime);

        validateAppointmentDateTimeAlreadyBooked(appointmentDateTime, appointment);

        validateUserAlreadyHaveAnAppointment(appointmentDateTime, appointment);
    }

    private void validateAppointmentInPast(LocalDateTime appointmentDateTime) {
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentDateTimeException();
        }
    }

    private void validateAppointmentOutOfBusinessHour(LocalDateTime appointmentDateTime) {
        BusinessHour businessHour = businessHourService.getHoursFor(appointmentDateTime.toLocalDate());
        LocalTime hour = appointmentDateTime.toLocalTime();

        LocalTime start = businessHour.getOpensAt();
        LocalTime end = businessHour.getClosesAt();

        if (hour.isBefore(start) || hour.equals(end) || hour.isAfter(end)) {
            throw new AppointmentOutOfBusinessHourException(start, end);
        }
    }

    private void validateAppointmentDateTimeAlreadyBooked(LocalDateTime appointmentDateTime, Appointment appointment) {
        if (appointmentRepository.existsByAppointmentDateTime(appointmentDateTime)) {

            User professional = userRepository.findById(appointment.getProfessionalId()).orElseThrow(
                    () -> new UserNotFoundException(appointment.getProfessionalId())
            );

            List<Appointment> appointmentsAtDateTime = appointmentRepository.findByAppointmentDateTime(appointmentDateTime).orElseThrow(
                    () -> new ResourceNotFoundException("appointment", appointmentDateTime)
            );

            Set<UUID> professionalsIdsAtDateTime = appointmentsAtDateTime
                    .stream()
                    .map(Appointment::getProfessionalId)
                    .collect(Collectors.toSet());

            if (professionalsIdsAtDateTime.contains(professional.getId())) {
                throw new AppointmentDateTimeAlreadyBookedException(professional.getName(), appointmentDateTime);
            }

        }
    }

    private void validateUserAlreadyHaveAnAppointment(LocalDateTime appointmentDateTime, Appointment appointment) {
        User customer = userRepository.findById(appointment.getCustomerId()).orElseThrow(
                () -> new UserNotFoundException(appointment.getCustomerId())
        );

        List<Appointment> appointmentsAtDateTime = appointmentRepository.findByAppointmentDateTime(appointmentDateTime).orElseThrow(
                () -> new ResourceNotFoundException("appointment", appointmentDateTime)
        );

        Set<UUID> customersIdsAtDateTime = appointmentsAtDateTime
                .stream()
                .map(Appointment::getCustomerId)
                .collect(Collectors.toSet());

        if (customersIdsAtDateTime.contains(customer.getId())) {
            throw new InvalidAppointmentDateTimeException(customer.getName(), appointmentDateTime);
        }
    }

}
