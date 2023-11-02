package com.example.carsharingservice.mapper;

import com.example.carsharingservice.condig.MapperConfig;
import com.example.carsharingservice.dto.user.UserRegistrationDto;
import com.example.carsharingservice.dto.user.UserResponseDto;
import com.example.carsharingservice.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toModel(UserRegistrationDto dto);
}
