package com.phellipe.app_de_agendamento.mapper;

import com.phellipe.app_de_agendamento.dto.agenda.AgendaRequestDto;
import com.phellipe.app_de_agendamento.dto.agenda.AgendaResponseDto;
import com.phellipe.app_de_agendamento.model.Agenda;

public class AgendaMapper {

    public static AgendaResponseDto toDto(Agenda agenda) {

        return new AgendaResponseDto(
                agenda.getId(),
                agenda.getName(),
                agenda.getWorkdayStart(),
                agenda.getWorkdayEnd(),
                agenda.getOwner().getId()
        );

    }

    public static Agenda toEntity(AgendaRequestDto dto) {

        return new Agenda(
                dto.name(),
                dto.workdayStart(),
                dto.workdayEnd()
        );

    }

}
