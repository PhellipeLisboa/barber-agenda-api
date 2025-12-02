package com.phellipe.barber_agenda_api.mapper;

import com.phellipe.barber_agenda_api.dto.appointment.AppointmentRequestDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentResponseDto;
import com.phellipe.barber_agenda_api.model.Appointment;

public class AppointmentMapper {

    public static AppointmentResponseDto toDto(Appointment appointment) {

        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getCustomerId(),
                appointment.getCustomerName(),
                appointment.getProfessionalId(),
                appointment.getProfessionalName(),
                appointment.getAppointmentDateTime()
        );

    }

    public static Appointment toEntity(AppointmentRequestDto dto) {

        return new Appointment(
                dto.customerId(),
                dto.professionalId(),
                dto.appointmentDateTime()
        );

    }

}
