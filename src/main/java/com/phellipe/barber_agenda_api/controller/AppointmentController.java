package com.phellipe.barber_agenda_api.controller;

import com.phellipe.barber_agenda_api.docs.AppointmentControllerDocs;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentPatchDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentRequestDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentResponseDto;
import com.phellipe.barber_agenda_api.infra.config.ApiPaths;
import com.phellipe.barber_agenda_api.service.BusinessHourService;
import com.phellipe.barber_agenda_api.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/appointments")
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController implements AppointmentControllerDocs {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@Valid @RequestBody AppointmentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> findAppointments() {
        return ResponseEntity.ok().body(appointmentService.findAll());
    }

    @GetMapping("/{appointment_id}")
    @PreAuthorize("@AppointmentSecurity.canGetAppointment(#appointment_id)")
    public ResponseEntity<AppointmentResponseDto> findAppointmentById(@PathVariable Long appointment_id) {
        return ResponseEntity.ok().body(appointmentService.findById(appointment_id));
    }

    @PatchMapping("/{appointment_id}")
    @PreAuthorize("@AppointmentSecurity.canManageAppointment(#appointment_id)")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable Long appointment_id,
                                                                    @Valid @RequestBody AppointmentPatchDto dto) {

        return ResponseEntity.ok().body(appointmentService.update(appointment_id, dto));
    }

    @DeleteMapping("/{appointment_id}")
    @PreAuthorize("@AppointmentSecurity.canManageAppointment(#appointment_id)")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointment_id) {
        appointmentService.delete(appointment_id);
        return ResponseEntity.ok().build();
    }



}
