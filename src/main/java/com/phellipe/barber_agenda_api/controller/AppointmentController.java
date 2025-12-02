package com.phellipe.barber_agenda_api.controller;

import com.phellipe.barber_agenda_api.dto.appointment.AppointmentPatchDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentRequestDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentResponseDto;
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
@RequestMapping("/appointments")
@SecurityRequirement(name = "bearerAuth")
@ApiResponse(responseCode = "401", description = "Ação não autorizada.")
@Tag(name = "appointments", description = "Controlador para criar e editar agendamentos")
public class AppointmentController {

    private final BusinessHourService businessHourService;
    private final AppointmentService appointmentService;

    public AppointmentController(BusinessHourService businessHourService, AppointmentService appointmentService) {
        this.businessHourService = businessHourService;
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(operationId = "01-createAppointment", summary = "Criar agendamento", description = "Método para criar um agendamento.")
    @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento inválido (Agendamento no passado).")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento inválido (Agendamento fora do expediente).")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento é obrigatório.")
    @ApiResponse(responseCode = "400", description = "Id do cliente é obrigatório.")
    @ApiResponse(responseCode = "400", description = "Id do profissional é obrigatório.")
    @ApiResponse(responseCode = "400", description = "Apenas usuários com role PROFESSIONAL podem ser passados como profissional.")
    @ApiResponse(responseCode = "409", description = "Horário de agendamento indisponível.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AppointmentResponseDto> createAppointment(@Valid @RequestBody AppointmentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.save(dto));
    }

    @GetMapping
    @Operation(operationId = "02-listAppointments", summary = "Listar agendamentos", description = "Método para listar agendamentos.")
    @ApiResponse(responseCode = "200", description = "Agendamentos encontrados com sucesso.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<List<AppointmentResponseDto>> findAppointments() {
        return ResponseEntity.ok().body(appointmentService.findAll());
    }

    @GetMapping("{appointment_id}")
    @PreAuthorize("@AppointmentSecurity.canGetAppointment(#appointment_id)")
    @Operation(operationId = "03-getAppointmentById", summary = "Buscar agendamento", description = "Método para buscar um agendamento.")
    @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AppointmentResponseDto> findAppointmentById(@PathVariable Long appointment_id) {
        return ResponseEntity.ok().body(appointmentService.findById(appointment_id));
    }

    @PatchMapping("{appointment_id}")
    @PreAuthorize("@AppointmentSecurity.canManageAppointment(#appointment_id)")
    @Operation(operationId = "04-updateAppointment", summary = "Alterar dados de agendamento", description = "Método para alterar dados de um agendamento.")
    @ApiResponse(responseCode = "201", description = "Dados do agendamento alterados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrada.")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento inválido (Agendamento no passado).")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento inválido (Agendamento fora do expediente).")
    @ApiResponse(responseCode = "400", description = "Nome do cliente deve ter entre 4 e 30 caracteres.")
    @ApiResponse(responseCode = "400", description = "Nome do profissional deve ter entre 4 e 30 caracteres.")
    @ApiResponse(responseCode = "409", description = "Horário de agendamento indisponível.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable Long appointment_id,
                                                                    @Valid @RequestBody AppointmentPatchDto dto) {

        return ResponseEntity.ok().body(appointmentService.update(appointment_id, dto));
    }

    @DeleteMapping("{appointment_id}")
    @PreAuthorize("@AppointmentSecurity.canManageAppointment(#appointment_id)")
    @Operation(operationId = "5-deleteAppointment", summary = "Deletar agendamento", description = "Método para deletar um agendamento.")
    @ApiResponse(responseCode = "200", description = "Agendamento deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointment_id) {
        appointmentService.delete(appointment_id);
        return ResponseEntity.ok().build();
    }



}
