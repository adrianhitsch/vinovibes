package com.vinovibes.vinoapi.mappers;

import com.vinovibes.vinoapi.dtos.SignUpDto;
import com.vinovibes.vinoapi.dtos.UserDto;
import com.vinovibes.vinoapi.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "token", ignore = true)
    public UserDto toUserDto(User user);

    public User signUpToUser(SignUpDto signUpDto);
}
