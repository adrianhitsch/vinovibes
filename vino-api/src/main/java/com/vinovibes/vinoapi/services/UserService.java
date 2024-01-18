package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.dtos.CredentialsDto;
import com.vinovibes.vinoapi.dtos.SignUpDto;
import com.vinovibes.vinoapi.dtos.UserDto;
import com.vinovibes.vinoapi.dtos.VerificationDto;
import com.vinovibes.vinoapi.dtos.errors.ErrorDto;
import com.vinovibes.vinoapi.dtos.errors.UserErrorDto;
import com.vinovibes.vinoapi.entities.User;
import com.vinovibes.vinoapi.enums.UserStatus;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.mappers.UserMapper;
import com.vinovibes.vinoapi.repositories.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final Map<String, String> email_token = new HashMap<>(); // this is for email verification

    public User login(CredentialsDto credentialsDto) {
        Optional<User> user = userRepository.findByEmail(credentialsDto.email());

        if (user.isEmpty() || !passwordEncoder.matches(credentialsDto.password(), user.get().getPassword())) {
            throw new AppException("Unknown user", HttpStatus.UNAUTHORIZED);
        }

        if (isUserPending(user.get())) {
            throwUserPendingException();
        }

        return user.get();
    }

    public User register(SignUpDto signUpDto) {
        checkSignUpDto(signUpDto);

        Optional<User> existingUser = userRepository.findByEmail(signUpDto.email());
        if (existingUser.isPresent()) {
            throw new AppException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(signUpDto.password()));
        user.setStatus(UserStatus.PENDING);
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void checkSignUpDto(SignUpDto signUpDto) {
        if (
            signUpDto.firstName().isEmpty() ||
            signUpDto.lastName().isEmpty() ||
            signUpDto.email().isEmpty() ||
            signUpDto.password().isEmpty() ||
            signUpDto.passwordRepeat().isEmpty()
        ) {
            throw new AppException("Please fill out all fields", HttpStatus.BAD_REQUEST);
        }

        if (!signUpDto.password().equals(signUpDto.passwordRepeat())) {
            throw new AppException("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (!signUpDto.eighteen() || !signUpDto.privacy()) {
            throw new AppException("You must agree to all terms and conditions", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isUserPending(User user) {
        return user.getStatus() == UserStatus.PENDING;
    }

    private void throwUserPendingException() throws AppException {
        UserErrorDto userErrorDto = new UserErrorDto(UserStatus.PENDING.name());
        throw new AppException("Please verify your email", HttpStatus.BAD_REQUEST, userErrorDto);
    }
}
