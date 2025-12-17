package com.phellipe.barber_agenda_api.testutil;

import com.phellipe.barber_agenda_api.model.BusinessHour;

import java.time.LocalTime;

public class BusinessHourTestConstants {

    public static final BusinessHour DEFAULT_BUSINESS_HOUR = create();

    private static BusinessHour create() {
        BusinessHour businessHour = new BusinessHour();
        businessHour.setOpensAt(LocalTime.of(9,0));
        businessHour.setClosesAt(LocalTime.of(18,0));
        return businessHour;
    }

}
