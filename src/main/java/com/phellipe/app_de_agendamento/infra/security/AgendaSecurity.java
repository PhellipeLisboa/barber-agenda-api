package com.phellipe.app_de_agendamento.infra.security;

import com.phellipe.app_de_agendamento.model.user.User;
import com.phellipe.app_de_agendamento.repository.UserRepository;
import com.phellipe.app_de_agendamento.service.AgendaService;
import com.phellipe.app_de_agendamento.service.AuthService;
import org.springframework.stereotype.Component;


@Component("AgendaSecurity")
public class AgendaSecurity {

    private final AgendaService agendaService;
    private final AuthService authService;

    public AgendaSecurity(AgendaService agendaService, UserRepository userRepository, AuthService authService) {
        this.agendaService = agendaService;
        this.authService = authService;
    }

    public boolean canCreateAgenda() {
        User user = authService.getAuthenticatedUser();
        if (isAdmin(user)) return true;
        return isOwner(user);
    }

    public boolean canManageAgenda(Long agendaId) {
        User user = authService.getAuthenticatedUser();
        if (isAdmin(user)) return true;
        return agendaService.findById(agendaId).owner().equals(user.getId());
    }

    public boolean canCreateAppointments(Long agendaId) {
        User user = authService.getAuthenticatedUser();

        if (isAdmin(user)) return true;

        if (isOwner(user)) {
            return agendaService.findById(agendaId).owner().equals(user.getId());
        }

        if (isProfessional(user)) {

            //return appointmentService;
            return false;
        }

        return false;
    }

    public boolean canUpdateAppointments() {
        return false;
    }
    public boolean canDeleteAppointments() {
        return false;
    }
    public boolean canCreateAppointments() {
        return false;
    }
    public boolean canFindAppointment() {
        return false;
    }
    public boolean canListAppointments() {
        return false;
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
