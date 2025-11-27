package com.phellipe.app_de_agendamento.mapper;

import com.phellipe.app_de_agendamento.dto.appointment.AppointmentRequestDto;
import com.phellipe.app_de_agendamento.dto.appointment.AppointmentResponseDto;
import com.phellipe.app_de_agendamento.model.Appointment;

public class AppointmentMapper {

    public static AppointmentResponseDto toDto(Appointment appointment) {

        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getAgenda().getId(),
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
