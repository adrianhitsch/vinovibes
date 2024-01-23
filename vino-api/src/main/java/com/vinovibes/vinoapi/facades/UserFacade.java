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

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final OTPService otpService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userService.login(credentialsDto);
        return userMapper.toUserDto(user);
    }

    public UserDto register(SignUpDto signUpDto) {
        User user = userService.register(signUpDto);
        try {
            user.setOtp(otpService.generateOTP());
        } catch (AppException e) {
            user.setOtp(null);
        }

        user = userService.save(user);
        // TODO: make email sending async
        emailService.sendVerificationEmail(user);
        return userMapper.toUserDto(user);
    }

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

    public UserDto getCurrentUser() {
        User user = userService.getCurrentUser();
        return userMapper.toUserDto(user);
    }

    public void forgotPassword(EmailDto emailDto) {
        User user = userService
            .getUserByEmail(emailDto.email())
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        user.setStatus(UserStatus.FORGOT_PASSWORD);
        user.setToken(tokenService.generateNewToken());
        user = userService.save(user);
        emailService.sendForgotPasswordEmail(user);
    }

    public UserDto resetPassword(PasswordResetDto passwordResetDto) {
        boolean valid = userService.checkPasswordResetDto(passwordResetDto);
        if (!valid) {
            throw new AppException("Invalid password reset request", HttpStatus.BAD_REQUEST);
        }

        Token token = tokenService
            .getTokenByValue(passwordResetDto.token())
            .orElseThrow(() -> new AppException("Unknown token", HttpStatus.BAD_REQUEST));

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
