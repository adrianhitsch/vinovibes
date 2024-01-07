package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.dtos.CredentialsDto;
import com.vinovibes.vinoapi.dtos.SignUpDto;
import com.vinovibes.vinoapi.dtos.UserDto;

import com.vinovibes.vinoapi.entities.User;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.mappers.UserMapper;
import com.vinovibes.vinoapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByEmail(credentialsDto.email())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialsDto.password(), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Unknown user", HttpStatus.BAD_REQUEST);

    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<User> exisitingUser = userRepository.findByEmail(signUpDto.email());

        if (exisitingUser.isPresent()) {
            throw new AppException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(signUpDto.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);

    }

}