package com.phellipe.barber_agenda_api.mapper;

import com.phellipe.barber_agenda_api.dto.UserResponseDto;
import com.phellipe.barber_agenda_api.model.user.User;

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
