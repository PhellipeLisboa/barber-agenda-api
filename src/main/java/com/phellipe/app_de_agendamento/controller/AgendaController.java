package com.phellipe.app_de_agendamento.controller;

import com.phellipe.app_de_agendamento.dto.AddProfessionalDto;
import com.phellipe.app_de_agendamento.dto.UserResponseDto;
import com.phellipe.app_de_agendamento.dto.agenda.AgendaPatchDto;
import com.phellipe.app_de_agendamento.dto.agenda.AgendaRequestDto;
import com.phellipe.app_de_agendamento.dto.agenda.AgendaResponseDto;
import com.phellipe.app_de_agendamento.dto.appointment.AppointmentPatchDto;
import com.phellipe.app_de_agendamento.dto.appointment.AppointmentRequestDto;
import com.phellipe.app_de_agendamento.dto.appointment.AppointmentResponseDto;
import com.phellipe.app_de_agendamento.service.AgendaService;
import com.phellipe.app_de_agendamento.service.AppointmentService;
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
import java.util.UUID;

@RestController
@RequestMapping("/agenda")
@SecurityRequirement(name = "bearerAuth")
@ApiResponse(responseCode = "401", description = "Ação não autorizada.")
@Tag(name = "Agenda", description = "Controlador para criar e editar agendas e agendamentos")
public class AgendaController {

    private final AgendaService agendaService;
    private final AppointmentService appointmentService;

    public AgendaController(AgendaService agendaService, AppointmentService appointmentService) {
        this.agendaService = agendaService;
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(operationId = "01-createAgenda",summary = "Criar agenda", description = "Método para salvar dados de uma nova agenda.")
    @ApiResponse(responseCode = "201", description = "Agenda criada com sucesso.")
    @ApiResponse(responseCode = "400", description = "O nome do estabelecimento é obrigatório.")
    @ApiResponse(responseCode = "400", description = "O nome do estabelecimento deve ter entre 4 e 30 caracteres.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AgendaResponseDto> save(@Valid @RequestBody AgendaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.save(dto));
    }

    @GetMapping
    @Operation(operationId = "02-listAgendas", summary = "Listar agendas", description = "Método para listar todas as agendas cadastradas.")
    @ApiResponse(responseCode = "200", description = "Agendas encontradas com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<List<AgendaResponseDto>> findAll() {
        return ResponseEntity.ok().body(agendaService.findAll());
    }

    @GetMapping("/{agenda_id}")
    @Operation(operationId = "03-getAgendaById", summary = "Buscar agenda", description = "Método buscar uma agenda cadastrada.")
    @ApiResponse(responseCode = "200", description = "Agenda encontrada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AgendaResponseDto> findById(@PathVariable Long agenda_id) {
        return ResponseEntity.ok().body(agendaService.findById(agenda_id));
    }

    @PreAuthorize("@AgendaSecurity.canManageAgenda(#agenda_id)")
    @PatchMapping("/{agenda_id}")
    @Operation(operationId = "04-updateAgenda", summary = "Alterar dados da agenda", description = "Método para alterar dados de uma agenda.")
    @ApiResponse(responseCode = "200", description = "Dados da agenda alterados com sucesso.")
    @ApiResponse(responseCode = "400", description = "O nome do estabelecimento deve ter entre 4 e 30 caracteres.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AgendaResponseDto> update(@PathVariable Long agenda_id, @Valid @RequestBody AgendaPatchDto dto) {
        return ResponseEntity.ok().body(agendaService.update(agenda_id, dto));
    }

    @PreAuthorize("@AgendaSecurity.canManageAgenda(#agenda_id)")
    @DeleteMapping("/{agenda_id}")
    @Operation(operationId = "05-deleteAgenda", summary = "Deletar agenda", description = "Método para deletar dados de uma agenda.")
    @ApiResponse(responseCode = "200", description = "Dados da agenda alterados com sucesso.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<Void> delete(@PathVariable Long agenda_id) {
        agendaService.delete(agenda_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{agenda_id}/appointments")
    @Operation(operationId = "06-createAppointment", summary = "Criar agendamento", description = "Método para criar um agendamento.")
    @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento inválido (Agendamento no passado).")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento inválido (Agendamento fora do expediente).")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento é obrigatório.")
    @ApiResponse(responseCode = "400", description = "Nome do cliente é obrigatório.")
    @ApiResponse(responseCode = "400", description = "Nome do cliente deve ter entre 4 e 30 caracteres.")
    @ApiResponse(responseCode = "400", description = "Nome do profissional é obrigatório.")
    @ApiResponse(responseCode = "400", description = "Nome do profissional deve ter entre 4 e 30 caracteres.")
    @ApiResponse(responseCode = "409", description = "Horário de agendamento indisponível.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AppointmentResponseDto> createAppointment(@PathVariable Long agenda_id, @Valid @RequestBody AppointmentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.save(agenda_id, dto));
    }

    @GetMapping("/{agenda_id}/appointments")
    @Operation(operationId = "07-listAppointments", summary = "Listar agendamentos", description = "Método para listar agendamentos de uma agenda específica.")
    @ApiResponse(responseCode = "200", description = "Agendamentos encontrados com sucesso.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<List<AppointmentResponseDto>> findAppointments(@PathVariable Long agenda_id) {
        return ResponseEntity.ok().body(appointmentService.findAll(agenda_id));
    }

    @GetMapping("/{agenda_id}/appointments/{appointment_id}")
    @Operation(operationId = "08-getAppointmentById", summary = "Buscar agendamento", description = "Método para buscar um agendamento de uma agenda específica.")
    @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AppointmentResponseDto> findAppointmentById(@PathVariable Long agenda_id, @PathVariable Long appointment_id) {
        return ResponseEntity.ok().body(appointmentService.findById(agenda_id, appointment_id));
    }

    @PatchMapping("/{agenda_id}/appointments/{appointment_id}")
    @Operation(operationId = "09-updateAppointment", summary = "Alterar dados de agendamento", description = "Método para alterar dados de um agendamento.")
    @ApiResponse(responseCode = "201", description = "Dados do agendamento alterados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrada.")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento inválido (Agendamento no passado).")
    @ApiResponse(responseCode = "400", description = "Horário de agendamento inválido (Agendamento fora do expediente).")
    @ApiResponse(responseCode = "400", description = "Nome do cliente deve ter entre 4 e 30 caracteres.")
    @ApiResponse(responseCode = "400", description = "Nome do profissional deve ter entre 4 e 30 caracteres.")
    @ApiResponse(responseCode = "409", description = "Horário de agendamento indisponível.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable Long agenda_id,
                                                                    @PathVariable Long appointment_id,
                                                                    @Valid @RequestBody AppointmentPatchDto dto) {

        return ResponseEntity.ok().body(appointmentService.update(agenda_id, appointment_id, dto));
    }

    @DeleteMapping("/{agenda_id}/appointments/{appointment_id}")
    @Operation(operationId = "10-deleteAppointment", summary = "Deletar agendamento", description = "Método para deletar um agendamento de uma agenda específica.")
    @ApiResponse(responseCode = "200", description = "Agendamento deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long agenda_id, @PathVariable Long appointment_id) {
        appointmentService.delete(agenda_id, appointment_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{agenda_id}/professionals")
    @PreAuthorize("@AgendaSecurity.canManageAgenda(#agenda_id)")
    @Operation(operationId = "11-listProfessionals", summary = "Listar profissionais", description = "Método para listar profissionais de uma agenda específica.")
    @ApiResponse(responseCode = "200", description = "Profissionais encontrados com sucesso.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    public ResponseEntity<List<UserResponseDto>> getProfessionals(@PathVariable Long agenda_id) {
        return ResponseEntity.ok(agendaService.getProfessionals(agenda_id));
    }

    @GetMapping("/{agenda_id}/professionals/{professional_id}")
    @PreAuthorize("@AgendaSecurity.canManageAgenda(#agenda_id)")
    @Operation(operationId = "12-getProfessionalById", summary = "Buscar profissional", description = "Método para buscar um profissional de uma agenda específica.")
    @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "404", description = "Profissional não encontrado.")
    public ResponseEntity<UserResponseDto> getProfessionalById(@PathVariable Long agenda_id, @PathVariable UUID professional_id) {
        return ResponseEntity.ok(agendaService.getProfessionalById(agenda_id, professional_id));
    }

    @PostMapping("/{agenda_id}/professionals")
    @PreAuthorize("@AgendaSecurity.canManageAgenda(#agenda_id)")
    @Operation(operationId = "13-addProfessional", summary = "Adicionar profissional", description = "Método para adicionar um profissional a uma agenda específica.")
    @ApiResponse(responseCode = "200", description = "Profissional adicionado com sucesso.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "400", description = "Usuário passado não possui a role PROFESSIONAL.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    public ResponseEntity<UserResponseDto> addProfessional(@PathVariable Long agenda_id, @RequestBody AddProfessionalDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.addProfessional(agenda_id, dto));
    }

    @DeleteMapping("/{agenda_id}/professionals/{professional_id}")
    @PreAuthorize("@AgendaSecurity.canManageAgenda(#agenda_id)")
    @Operation(operationId = "14-removeProfessional", summary = "Remover profissional", description = "Método para remover um profissional de uma agenda específica.")
    @ApiResponse(responseCode = "200", description = "Profissional removido com sucesso.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada.")
    @ApiResponse(responseCode = "404", description = "Profissional não encontrado.")
    public ResponseEntity<Void> removeProfessional(@PathVariable Long agenda_id, @PathVariable UUID professional_id) {
        agendaService.removeProfessional(agenda_id, professional_id);
        return ResponseEntity.ok().build();
    }


}
