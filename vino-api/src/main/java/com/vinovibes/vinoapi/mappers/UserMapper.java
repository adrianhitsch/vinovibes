package com.vinovibes.vinoapi.mappers;

import org.springframework.stereotype.Service;

import com.vinovibes.vinoapi.dtos.SignUpDto;
import com.vinovibes.vinoapi.dtos.UserDto;
import com.vinovibes.vinoapi.entities.User;

@Service
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public User signUpToUser(SignUpDto signUpDto) {
        return User.builder()
                .firstName(signUpDto.firstName())
                .lastName(signUpDto.lastName())
                .email(signUpDto.email())
                .build();
    }
}