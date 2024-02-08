package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.dtos.errors.UserErrorDto;
import com.vinovibes.vinoapi.dtos.user.CredentialsDto;
import com.vinovibes.vinoapi.dtos.user.PasswordResetDto;
import com.vinovibes.vinoapi.dtos.user.SignUpDto;
import com.vinovibes.vinoapi.entities.user.Token;
import com.vinovibes.vinoapi.entities.user.User;
import com.vinovibes.vinoapi.enums.UserStatus;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.mappers.UserMapper;
import com.vinovibes.vinoapi.repositories.UserRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for users.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * Method for logging in a user. If the user does not exist or the password is incorrect, an exception is thrown.
     * If the user status is PENDING or FORGOT_PASSWORD, an exception is thrown.
     * @param credentialsDto credentials DTO
     * @return user
     */
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

    /**
     * Method for registering a new user. If the email already exists, an exception is thrown.
     * The signUpDto is mapped to a user and the password is encoded.
     * The user status is set to PENDING.
     * returns the user.
     * @param signUpDto sign up DTO
     * @return user
     */
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

    /**
     * Method for getting a user by email.
     * @param email email
     * @return user
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Method for getting a user by token.
     * @param token token
     * @return user
     */
    public Optional<User> getUserByToken(Token token) {
        return userRepository.findByToken(token);
    }

    /**
     * Method for saving a user. If the user is null, an exception is thrown.
     * @param user user
     * @return user
     */
    public User save(User user) {
        Objects.requireNonNull(user, "User must not be null");
        return userRepository.save(user);
    }

    /**
     * Method for getting the current user.
     * @return user
     */
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Method for checking the passwordResetDto.
     * @param passwordResetDto passwordResetDto
     * @return boolean
     */
    public boolean checkPasswordResetDto(PasswordResetDto passwordResetDto) {
        return passwordResetDto.password().equals(passwordResetDto.passwordRepeat());
    }

    /**
     * Method for checking the signUpDto.
     * If the passwords do not match or the terms and conditions are not agreed to, an exception is thrown.
     * @param signUpDto signUpDto
     */
    private void checkSignUpDto(SignUpDto signUpDto) {
        if (!signUpDto.password().equals(signUpDto.passwordRepeat())) {
            throw new AppException("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (!signUpDto.eighteen() || !signUpDto.privacy()) {
            throw new AppException("You must agree to all terms and conditions", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method for matching a user status.
     * @param user user
     * @param status status
     * @return boolean
     */
    private boolean matchUserStatus(User user, UserStatus status) {
        return user.getStatus() == status;
    }

    /**
     * Method for throwing a user status exception.
     * @param status status
     * @param message message
     * @throws AppException app exception
     */
    private void throwUserStatusException(UserStatus status, String message) throws AppException {
        UserErrorDto userErrorDto = new UserErrorDto(status.name());
        throw new AppException(message, HttpStatus.BAD_REQUEST, userErrorDto);
    }

    /**
     * Method for getting a user by id.
     * @param userId user id
     * @return user
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
