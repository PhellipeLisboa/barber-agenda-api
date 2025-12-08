package com.phellipe.barber_agenda_api.docs;

import com.phellipe.barber_agenda_api.dto.appointment.AppointmentPatchDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentRequestDto;
import com.phellipe.barber_agenda_api.dto.appointment.AppointmentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@ApiResponse(responseCode = "401", description = "Ação não autorizada.")
@Tag(name = "appointments", description = "Endpoints para criar e editar agendamentos.")
public interface AppointmentControllerDocs {

    @Operation(summary = "Criar agendamento", description = "Registra um novo agendamento.")
    @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso.")
    @ApiResponse(responseCode = "400", description = """
            Horário de agendamento inválido (Agendamento no passado).
            
            Horário de agendamento inválido (Agendamento fora do expediente).
            
            Horário de agendamento é obrigatório.
            
            Id do cliente é obrigatório.
            
            Id do profissional é obrigatório.
            
            Apenas usuários com role PROFESSIONAL podem ser passados como profissional.
            """)
    @ApiResponse(responseCode = "409", description = "Horário de agendamento indisponível.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<AppointmentResponseDto> createAppointment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do novo agendamento para registro.")
            @RequestBody @Valid AppointmentRequestDto dto
    );

    @Operation(summary = "Listar agendamentos", description = "Retorna todos os agendamentos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Agendamentos encontrados com sucesso.")
    @ApiResponse(responseCode = "403", description = "O usuário não tem permissão para acessar esse recurso.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<List<AppointmentResponseDto>> findAppointments();

    @Operation(summary = "Buscar agendamento", description = "Busca um agendamento pelo ID.")
    @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<AppointmentResponseDto> findAppointmentById(
            @Parameter(description = "ID do agendamento para realizar a busca.")
            @PathVariable("appointment_id") Long appointment_id
    );

    @Operation(summary = "Alterar dados de agendamento", description = "Atualiza os dados de um agendamento.")
    @ApiResponse(responseCode = "200", description = "Dados do agendamento alterados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrada.")
    @ApiResponse(responseCode = "400", description = """
            Horário de agendamento inválido (Agendamento no passado).
            
            Horário de agendamento inválido (Agendamento fora do expediente).
            
            Nome do cliente deve ter entre 4 e 30 caracteres.
            
            Nome do profissional deve ter entre 4 e 30 caracteres.
            """)
    @ApiResponse(responseCode = "409", description = "Horário de agendamento indisponível.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<AppointmentResponseDto> updateAppointment(
            @Parameter(description = "ID do agendamento para realizar a atualização.")
            @PathVariable("appointment_id") Long appointment_id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do agendamento para realizar a atualização.")
            @RequestBody @Valid AppointmentPatchDto dto
    );

    @Operation(summary = "Deletar agendamento", description = "Remove um agendamento pelo ID.")
    @ApiResponse(responseCode = "200", description = "Agendamento deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    ResponseEntity<Void> deleteAppointment(
            @Parameter(description = "ID do agendamento para deletar.")
            @PathVariable("appointment_id") Long appointment_id
    );

}
