package com.phellipe.barber_agenda_api.repository;

import com.phellipe.barber_agenda_api.model.BusinessHour;
import com.phellipe.barber_agenda_api.model.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessHourRepository extends JpaRepository<BusinessHour, Long> {

    Optional<BusinessHour> findByDay(WeekDay day);

}
