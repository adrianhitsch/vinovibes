package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.dtos.CredentialsDto;
import com.vinovibes.vinoapi.dtos.PasswordResetDto;
import com.vinovibes.vinoapi.dtos.SignUpDto;
import com.vinovibes.vinoapi.dtos.errors.UserErrorDto;
import com.vinovibes.vinoapi.entities.Token;
import com.vinovibes.vinoapi.entities.User;
import com.vinovibes.vinoapi.enums.UserStatus;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.mappers.UserMapper;
import com.vinovibes.vinoapi.repositories.UserRepository;
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

    public User login(CredentialsDto credentialsDto) {
        Optional<User> user = userRepository.findByEmail(credentialsDto.email());

        if (user.isEmpty() || !passwordEncoder.matches(credentialsDto.password(), user.get().getPassword())) {
            throw new AppException("Unknown user", HttpStatus.UNAUTHORIZED);
        }

        if (matchUserStatus(user.get(), UserStatus.PENDING)) {
            throwUserStatusException(UserStatus.PENDING, "Please verify your email");
        } else if (matchUserStatus(user.get(), UserStatus.FORGOT_PASSWORD)) {
            throwUserStatusException(UserStatus.FORGOT_PASSWORD, "Please reset your password");
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

    public Optional<User> getUserByToken(Token token) {
        return userRepository.findByToken(token);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean checkPasswordResetDto(PasswordResetDto passwordResetDto) {
        return passwordResetDto.password().equals(passwordResetDto.passwordRepeat());
    }

    private void checkSignUpDto(SignUpDto signUpDto) {
        if (!signUpDto.password().equals(signUpDto.passwordRepeat())) {
            throw new AppException("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (!signUpDto.eighteen() || !signUpDto.privacy()) {
            throw new AppException("You must agree to all terms and conditions", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean matchUserStatus(User user, UserStatus status) {
        return user.getStatus() == status;
    }

    private void throwUserStatusException(UserStatus status, String message) throws AppException {
        UserErrorDto userErrorDto = new UserErrorDto(status.name());
        throw new AppException(message, HttpStatus.BAD_REQUEST, userErrorDto);
    }
}
