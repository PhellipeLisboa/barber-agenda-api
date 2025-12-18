package com.phellipe.barber_agenda_api.testutil;

import com.phellipe.barber_agenda_api.dto.appointment.AppointmentPatchDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentRequestDto;
import com.phellipe.barber_agenda_api.model.Appointment;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.phellipe.barber_agenda_api.testutil.UserTestConstants.*;

public class AppointmentTestConstants {

    public static final LocalDateTime NOW = LocalDateTime.of(2025, 1, 1, 10, 0);
    public static final LocalDateTime DATE_IN_THE_PAST = NOW.minusDays(1);
    public static final LocalDateTime DATE_OUT_OF_BUSINESS_HOUR = NOW.plusDays(1).withHour(22).withMinute(0);
    public static final LocalDateTime DATE_TWENTY_FIVE_HOURS_IN_FUTURE = NOW.plusHours(25);
    public static final LocalDateTime DATE_TWENTY_NINE_HOURS_IN_FUTURE = NOW.plusHours(29);
    public static final LocalDateTime DATE_TWENTY_THREE_HOURS_IN_FUTURE = NOW.plusHours(23);
    public static final Long APPOINTMENT_ID = 1L;
    public static final Long APPOINTMENT_WITH_OTHER_PROFESSIONAL_ID = 2L;
    public static final Long APPOINTMENT_TWENTY_FIVE_HOURS_IN_FUTURE_ID = 3L;
    public static final Long APPOINTMENT_TWENTY_THREE_HOURS_IN_FUTURE_ID = 4L;
    public static final Appointment APPOINTMENT = createEntity(APPOINTMENT_ID, CUSTOMER_ID, PROFESSIONAL_ID, NOW);
    public static final Appointment APPOINTMENT_WITH_OTHER_PROFESSIONAL = createEntity(APPOINTMENT_WITH_OTHER_PROFESSIONAL_ID, CUSTOMER_ID, OTHER_PROFESSIONAL_ID, NOW);
    public static final Appointment APPOINTMENT_TWENTY_FIVE_HOURS_IN_FUTURE = createEntity(APPOINTMENT_TWENTY_FIVE_HOURS_IN_FUTURE_ID, CUSTOMER_ID, PROFESSIONAL_ID, DATE_TWENTY_FIVE_HOURS_IN_FUTURE);
     public static final Appointment APPOINTMENT_TWENTY_THREE_HOURS_IN_FUTURE = createEntity(APPOINTMENT_TWENTY_THREE_HOURS_IN_FUTURE_ID, CUSTOMER_ID, PROFESSIONAL_ID, DATE_TWENTY_THREE_HOURS_IN_FUTURE);

    public static final AppointmentRequestDto APPOINTMENT_DTO = new AppointmentRequestDto(
            CUSTOMER_ID,
            PROFESSIONAL_ID,
            NOW
    );

    public static final AppointmentRequestDto APPOINTMENT_PROFESSIONAL_WITHOUT_ROLE_DTO = new AppointmentRequestDto(
            CUSTOMER_ID,
            PROFESSIONAL_WITHOUT_ROLE_ID,
            NOW
    );

    public static final AppointmentRequestDto APPOINTMENT_DATE_IN_THE_PAST_DTO = new AppointmentRequestDto(
            CUSTOMER_ID,
            PROFESSIONAL_ID,
            DATE_IN_THE_PAST
    );

    public static final AppointmentRequestDto APPOINTMENT_OUT_OF_BUSINESS_HOUR_DTO = new AppointmentRequestDto(
            CUSTOMER_ID,
            PROFESSIONAL_ID,
            DATE_OUT_OF_BUSINESS_HOUR
    );

    public static final AppointmentPatchDto APPOINTMENT_PATCH_DTO_TO_TWENTY_NINE_HOURS_IN_FUTURE = new AppointmentPatchDto(
                Optional.of(OTHER_PROFESSIONAL_ID),
                Optional.of(DATE_TWENTY_NINE_HOURS_IN_FUTURE)
    );

    public static final AppointmentPatchDto APPOINTMENT_PATCH_DTO_TO_TWENTY_THREE_HOURS_IN_FUTURE = new AppointmentPatchDto(
            Optional.of(OTHER_PROFESSIONAL_ID),
            Optional.of(DATE_TWENTY_THREE_HOURS_IN_FUTURE)
    );
    public static final AppointmentPatchDto APPOINTMENT_PATCH_DTO_TO_PROFESSIONAL_WITHOUT_ROLE = new AppointmentPatchDto(
            Optional.of(PROFESSIONAL_WITHOUT_ROLE_ID),
            Optional.of(DATE_TWENTY_NINE_HOURS_IN_FUTURE)
    );
    public static final AppointmentPatchDto APPOINTMENT_PATCH_DTO_TO_DATE_OUT_OF_BUSINESS_HOUR = new AppointmentPatchDto(
            Optional.of(OTHER_PROFESSIONAL_ID),
            Optional.of(DATE_OUT_OF_BUSINESS_HOUR)
    );


    public static final Appointment createEntity(Long appointmentId, UUID customerId, UUID professionalId, LocalDateTime appointmentDateTime) {
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setCustomerId(customerId);
        appointment.setProfessionalId(professionalId);
        appointment.setAppointmentDateTime(appointmentDateTime);
        return appointment;
    }


}
