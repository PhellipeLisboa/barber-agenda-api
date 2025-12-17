package com.phellipe.barber_agenda_api.testutil;

import com.phellipe.barber_agenda_api.model.user.Role;
import com.phellipe.barber_agenda_api.model.user.User;

import java.util.Set;
import java.util.UUID;

public class UserTestConstants {

    public static final UUID ADMIN_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID OWNER_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID PROFESSIONAL_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public static final UUID PROFESSIONAL_WITHOUT_ROLE_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");
    public static final UUID CUSTOMER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public static final UUID OTHER_CUSTOMER_ID = UUID.fromString("66666666-6666-6666-6666-666666666666");
    public static final UUID OTHER_PROFESSIONAL_ID = UUID.fromString("77777777-7777-7777-7777-777777777777");

    public static final User ADMIN = createAdmin();
    public static final User OWNER = createOwner();
    public static final User PROFESSIONAL = createProfessional();
    public static final User OTHER_PROFESSIONAL = createOtherProfessional();
    public static final User PROFESSIONAL_WITHOUT_ROLE = createProfessionalWithoutRole();
    public static final User CUSTOMER = createCustomer();
    public static final User OTHER_CUSTOMER = createOtherCustomer();

    private static User createAdmin() {
        User user = new User();
        user.setId(ADMIN_ID);
        user.setRoles(Set.of(
                        new Role("USER"),
                        new Role("ADMIN")
                ));
        return user;
    }

    private static User createOwner() {
        User user = new User();
        user.setId(OWNER_ID);
        user.setRoles(Set.of(
                        new Role("USER"),
                        new Role("OWNER")
                ));
        return user;
    }

    private static User createProfessional() {
        User user = new User();
        user.setName("professional");
        user.setId(PROFESSIONAL_ID);
        user.setRoles(Set.of(
                        new Role("USER"),
                        new Role("PROFESSIONAL")
                ));
        return user;
    }

    private static User createCustomer() {
        User user = new User();
        user.setName("customer");
        user.setId(CUSTOMER_ID);
        user.setRoles(Set.of(new Role("USER")));
        return user;
    }

    private static User createOtherCustomer() {
        User user = new User();
        user.setId(OTHER_CUSTOMER_ID);
        user.setRoles(Set.of(new Role("USER")));
        return user;
    }

    private static User createProfessionalWithoutRole() {
        User user = new User();
        user.setId(PROFESSIONAL_WITHOUT_ROLE_ID);
        user.setRoles(Set.of(
                new Role("USER")
        ));
        return user;
    }

    private static User createOtherProfessional() {
        User user = new User();
        user.setId(OTHER_PROFESSIONAL_ID);
        user.setRoles(Set.of(
                new Role("USER"),
                new Role("PROFESSIONAL")
        ));
        return user;
    }


}
