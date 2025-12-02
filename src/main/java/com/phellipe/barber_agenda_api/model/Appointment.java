package com.phellipe.barber_agenda_api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class Appointment {

    public Appointment() {}

    public Appointment(UUID customerId, UUID professionalId, LocalDateTime appointmentDateTime, String customerName, String professionalName) {
        this.customerId = customerId;
        this.professionalId = professionalId;
        this.appointmentDateTime = appointmentDateTime;
        this.customerName = customerName;
        this.professionalName = professionalName;
    }

    public Appointment(UUID customerId, UUID professionalId, LocalDateTime appointmentDateTime) {
        this.customerId = customerId;
        this.professionalId = professionalId;
        this.appointmentDateTime = appointmentDateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id")
    private UUID customerId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "professional_id")
    private UUID professionalId;
    @Column(name = "professional_name")
    private String professionalName;
    private LocalDateTime appointmentDateTime;

    public Long getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(UUID professionalId) {
        this.professionalId = professionalId;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }
}
