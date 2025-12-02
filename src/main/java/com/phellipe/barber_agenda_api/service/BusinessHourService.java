package com.phellipe.barber_agenda_api.service;

import com.phellipe.barber_agenda_api.exception.InvalidBusinessHourException;
import com.phellipe.barber_agenda_api.model.BusinessHour;
import com.phellipe.barber_agenda_api.model.WeekDay;
import com.phellipe.barber_agenda_api.repository.BusinessHourRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class BusinessHourService {

    private final BusinessHourRepository businessHourRepository;

    public BusinessHourService(BusinessHourRepository businessHourRepository) {
        this.businessHourRepository = businessHourRepository;
    }

    public BusinessHour getHoursFor(LocalDate date) {

        WeekDay day = WeekDay.from(date);

        return businessHourRepository.findByDay(day)
                .orElseThrow(() -> new RuntimeException("Horário não configurado para " + day));
    }

    public boolean isWithinBusinessHours(LocalDate date, LocalTime start, LocalTime end) {
        BusinessHour hours = getHoursFor(date);

        if (hours.isClosed()) return false;

        return !start.isBefore(hours.getOpensAt()) && !end.isAfter(hours.getClosesAt());
    }

    private void validateBusinessHours(LocalTime start, LocalTime end) {
        if (start.isAfter(end)) throw new InvalidBusinessHourException();
    }

}
