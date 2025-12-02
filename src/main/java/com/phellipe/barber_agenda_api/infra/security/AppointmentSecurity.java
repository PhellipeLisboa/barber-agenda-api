package com.phellipe.barber_agenda_api.infra.security;

import com.phellipe.barber_agenda_api.exception.ResourceNotFoundException;
import com.phellipe.barber_agenda_api.model.Appointment;
import com.phellipe.barber_agenda_api.model.user.User;
import com.phellipe.barber_agenda_api.repository.AppointmentRepository;
import com.phellipe.barber_agenda_api.repository.UserRepository;
import com.phellipe.barber_agenda_api.service.AppointmentService;
import com.phellipe.barber_agenda_api.service.BusinessHourService;
import com.phellipe.barber_agenda_api.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component("AppointmentSecurity")
public class AppointmentSecurity {

    private final BusinessHourService businessHourService;
    private final AppointmentRepository appointmentRepository;
    private final AuthService authService;

    public AppointmentSecurity(BusinessHourService businessHourService, AppointmentRepository appointmentRepository, AuthService authService) {
        this.businessHourService = businessHourService;
        this.appointmentRepository = appointmentRepository;
        this.authService = authService;
    }

    public boolean canGetAppointment(Long appointmentId) {
        User authenticatedUser = authService.getAuthenticatedUser();
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new ResourceNotFoundException("Appointment", appointmentId)
        );

        if (isAdmin(authenticatedUser)) return true;

        if (isOwner(authenticatedUser)) return true;

        if (isProfessional(authenticatedUser)) {
            return appointment.getProfessionalId().equals(authenticatedUser.getId());
        }

        return authenticatedUser.getId().equals(appointment.getCustomerId());
    }

    public boolean canManageAppointment(Long appointmentId) {
        User authenticatedUser = authService.getAuthenticatedUser();
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new ResourceNotFoundException("Appointment", appointmentId)
        );

        if (isAdmin(authenticatedUser)) return true;

        return authenticatedUser.getId().equals(appointment.getCustomerId());
    }

    private static boolean isAdmin(User user) {
        return user.hasRole("ADMIN");
    }
    private static boolean isOwner(User user) {
        return user.hasRole("OWNER");
    }
    private static boolean isProfessional(User user) {
        return user.hasRole("PROFESSIONAL");
    }

}
