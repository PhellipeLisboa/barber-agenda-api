package com.phellipe.barber_agenda_api.model;

import java.time.LocalDate;

public enum WeekDay {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public static WeekDay from (LocalDate date) {
        return WeekDay.valueOf(date.getDayOfWeek().name());
    }
}
