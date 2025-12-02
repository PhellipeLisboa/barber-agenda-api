package com.phellipe.barber_agenda_api.model;

import jakarta.persistence.*;

import java.time.LocalTime;


@Entity
@Table(name = "business_hours")
public class BusinessHour {

    public BusinessHour() {}

    public BusinessHour(WeekDay day, LocalTime workdayStart, LocalTime workdayEnd, boolean closed) {
        this.day = day;
        this.opensAt = workdayStart;
        this.closesAt = workdayEnd;
        this.closed = closed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WeekDay day;

    @Column(name = "opensAt")
    private LocalTime opensAt;

    @Column(name = "closesAt")
    private LocalTime closesAt;
    @Column(name = "is_closed")
    private boolean closed;

    public Long getId() {
        return id;
    }

    public LocalTime getOpensAt() {
        return opensAt;
    }

    public void setOpensAt(LocalTime workdayStart) {
        this.opensAt = workdayStart;
    }

    public LocalTime getClosesAt() {
        return closesAt;
    }

    public void setClosesAt(LocalTime workdayEnd) {
        this.closesAt = workdayEnd;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
