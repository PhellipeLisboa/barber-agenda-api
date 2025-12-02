package com.phellipe.barber_agenda_api.infra.security;

import com.phellipe.barber_agenda_api.model.user.User;
import com.phellipe.barber_agenda_api.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("UserSecurity")
public class UserSecurity {

    private final AuthService authService;

    public UserSecurity(AuthService authService) {
        this.authService = authService;
    }

    public boolean canManageUser(UUID targetUserId) {
        User authenticatedUser = authService.getAuthenticatedUser();

        if (isAdmin(authenticatedUser)) return true;

        if (isOwner(authenticatedUser)) return true;

        if (isProfessional(authenticatedUser)) return false;

        return authenticatedUser.getId().equals(targetUserId);
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
