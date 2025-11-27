package com.phellipe.app_de_agendamento.model;

import com.phellipe.app_de_agendamento.model.user.Role;
import com.phellipe.app_de_agendamento.model.user.User;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "agenda")
public class Agenda implements Serializable {

    public Agenda() {}

    public Agenda(String name, LocalTime workdayStart, LocalTime workdayEnd, User owner) {
        this.name = name;
        this.appointments = new ArrayList<Appointment>();
        this.workdayStart = workdayStart;
        this.workdayEnd = workdayEnd;
        this.owner = owner;
    }
    public Agenda(String name, LocalTime workdayStart, LocalTime workdayEnd) {
        this.name = name;
        this.appointments = new ArrayList<Appointment>();
        this.workdayStart = workdayStart;
        this.workdayEnd = workdayEnd;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @Column(name = "workday_start")
    private LocalTime workdayStart;

    @Column(name = "workday_end")
    private LocalTime workdayEnd;

    @OneToMany(mappedBy = "agenda")
    private List<Appointment> appointments;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "agenda_professionals",
            joinColumns = @JoinColumn(name = "agenda_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id")
    )
    private Set<User> professionals = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public LocalTime getWorkdayStart() {
        return workdayStart;
    }

    public void setWorkdayStart(LocalTime workdayStart) {
        this.workdayStart = workdayStart;
    }

    public LocalTime getWorkdayEnd() {
        return workdayEnd;
    }

    public void setWorkdayEnd(LocalTime workdayEnd) {
        this.workdayEnd = workdayEnd;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(Set<User> professionals) {
        this.professionals = professionals;
    }
}
