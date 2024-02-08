package com.vinovibes.vinoapi.facades;

import com.vinovibes.vinoapi.dtos.user.CredentialsDto;
import com.vinovibes.vinoapi.dtos.user.EmailDto;
import com.vinovibes.vinoapi.dtos.user.PasswordResetDto;
import com.vinovibes.vinoapi.dtos.user.SignUpDto;
import com.vinovibes.vinoapi.dtos.user.UserDto;
import com.vinovibes.vinoapi.dtos.user.VerificationDto;
import com.vinovibes.vinoapi.entities.user.Token;
import com.vinovibes.vinoapi.entities.user.User;
import com.vinovibes.vinoapi.enums.UserStatus;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.mappers.UserMapper;
import com.vinovibes.vinoapi.services.EmailService;
import com.vinovibes.vinoapi.services.OTPService;
import com.vinovibes.vinoapi.services.TokenService;
import com.vinovibes.vinoapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Facade for user authentication.
 */
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final OTPService otpService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    /**
     * Method for logging in a user.
     * @param credentialsDto credentialsDTO
     * @return userDTO
     */
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userService.login(credentialsDto);
        return userMapper.toUserDto(user);
    }

    /**
     * Method for registering a new user. If the Otp is set, the user is registered and an email is sent.
     * @param signUpDto signUpDTO
     * @return userDTO
     */
    public UserDto register(SignUpDto signUpDto) {
        User user = userService.register(signUpDto);
        try {
            user.setOtp(otpService.generateOTP());
        } catch (AppException e) {
            user.setOtp(null);
        }

        user = userService.save(user);
        emailService.sendVerificationEmail(user);
        return userMapper.toUserDto(user);
    }

    /**
     * Method for verifying an One Time Password. Checks if the user is already active. Checks if the OTP is valid.
     * Sets the user status to active and the OTP to null. Returns the userDTO.
     * @param verificationDto verificationDTO
     * @return userDTO
     */
    public UserDto verifyOTP(VerificationDto verificationDto) {
        User user = userService
            .getUserByEmail(verificationDto.email())
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        if (user.getStatus() == UserStatus.ACTIVE) {
            throw new AppException("User already active", HttpStatus.BAD_REQUEST);
        }

        if (user.getOtp() == null) {
            throw new AppException("OTP not found", HttpStatus.BAD_REQUEST);
        }

        boolean isValidOTP = otpService.validateOTP(verificationDto.otp(), user.getOtp());
        if (!isValidOTP) {
            throw new AppException("Invalid OTP", HttpStatus.BAD_REQUEST);
        }

        user.setStatus(UserStatus.ACTIVE);
        user.setOtp(null);
        user = userService.save(user);
        return userMapper.toUserDto(user);
    }

    /**
     * Method for requesting a new One Time Password. If the user is already active, an exception is thrown.
     * Generates a new OTP and sends an email to the user.
     * @param emailDto emailDTO
     */
    public void requestNewOTP(EmailDto emailDto) {
        User user = userService
            .getUserByEmail(emailDto.email())
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        if (user.getStatus() == UserStatus.ACTIVE) {
            throw new AppException("User already active", HttpStatus.BAD_REQUEST);
        }

        user.setOtp(otpService.generateOTP());
        user = userService.save(user);
        emailService.sendVerificationEmail(user);
    }

    /**
     * Method for getting the current user.
     * @return userDTO
     */
    public UserDto getCurrentUser() {
        User user = userService.getCurrentUser();
        return userMapper.toUserDto(user);
    }

    /**
     * Method for requesting a password reset. If the user is unknown, an exception is thrown.
     * Sets the user status to forgot password and generates a new token. Sends a password reset email to the user.
     * @param emailDto emailDTO
     */
    public void forgotPassword(EmailDto emailDto) {
        User user = userService
            .getUserByEmail(emailDto.email())
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        user.setStatus(UserStatus.FORGOT_PASSWORD);
        user.setToken(tokenService.generateNewToken());
        user = userService.save(user);
        emailService.sendForgotPasswordEmail(user);
    }

    /**
     * Method for resetting the password. If the passwordResetDTO is invalid, an exception is thrown.
     * Checks if the token is valid and not expired. Checks if the user is in forgot password state.
     * Sets the new password and the user status to active. Returns the userDTO.
     * @param passwordResetDto password reset DTO
     * @return userDTO
     */
    public UserDto resetPassword(PasswordResetDto passwordResetDto) {
        boolean valid = userService.checkPasswordResetDto(passwordResetDto);
        if (!valid) {
            throw new AppException("Invalid password reset request", HttpStatus.BAD_REQUEST);
        }

        Token token = tokenService
            .getTokenByValue(passwordResetDto.token())
            .orElseThrow(() -> new AppException("Unknown token", HttpStatus.BAD_REQUEST));
        if (tokenService.isTokenExpired(token)) {
            throw new AppException("Token expired", HttpStatus.BAD_REQUEST);
        }

        User user = token.getUser();
        if (user.getStatus() != UserStatus.FORGOT_PASSWORD) {
            throw new AppException("User not in forgot password state", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(passwordResetDto.password()));
        user.setStatus(UserStatus.ACTIVE);
        user.setToken(null);
        user = userService.save(user);
        return userMapper.toUserDto(user);
    }
}
