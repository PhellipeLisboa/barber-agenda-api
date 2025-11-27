package com.phellipe.app_de_agendamento.mapper;

import com.phellipe.app_de_agendamento.dto.UserResponseDto;
import com.phellipe.app_de_agendamento.model.user.User;

public class UserMapper {

    public static UserResponseDto toDto(User user) {

        return new UserResponseDto(
                user.getName(),
                user.getEmail(),
                user.getId(),
                user.getAuthorities().toString()
        );

    }


}
