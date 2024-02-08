package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.config.UserAuthProvider;
import com.vinovibes.vinoapi.dtos.user.CredentialsDto;
import com.vinovibes.vinoapi.dtos.user.EmailDto;
import com.vinovibes.vinoapi.dtos.user.PasswordResetDto;
import com.vinovibes.vinoapi.dtos.user.SignUpDto;
import com.vinovibes.vinoapi.dtos.user.UserDto;
import com.vinovibes.vinoapi.dtos.user.VerificationDto;
import com.vinovibes.vinoapi.facades.UserFacade;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user authentication.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final UserFacade userFacade;
    private final UserAuthProvider userAuthProvider;

    /**
     * Method for logging in a user. If the credentials are valid, the user is logged in and a JWT token is returned.
     * @param credentialsDto credentials DTO
     * @return user DTO
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody CredentialsDto credentialsDto) {
        UserDto user = userFacade.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    /**
     * Method for registering a new user. If the sign up DTO is valid, the user is registered.
     * @param signUpDto sign up DTO
     * @return user DTO
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody SignUpDto signUpDto) {
        UserDto user = userFacade.register(signUpDto);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(null);
    }

    /**
     * Method for verifying an One Time Password. If the verification DTO is valid, the user is verified and logged in.
     * @param verificationDto verification DTO
     * @return user DTO
     */
    @PostMapping("/register/otp")
    public ResponseEntity<UserDto> verifyOTP(@Valid @RequestBody VerificationDto verificationDto) {
        UserDto user = userFacade.verifyOTP(verificationDto);
        user.setToken(userAuthProvider.createToken(user)); // logs user in
        return ResponseEntity.ok(user);
    }

    /**
     * Method for requesting a new One Time Password. If the email DTO is valid, a new OTP is sent to the user.
     * @param emailDto email DTO
     * @return response entity
     */
    @PostMapping("/register/new-otp")
    public ResponseEntity<Void> requestNewOTP(@Valid @RequestBody EmailDto emailDto) {
        userFacade.requestNewOTP(emailDto);
        return ResponseEntity.ok(null);
    }

    /**
     * Method for refreshing the JWT token.
     * @return JWT token
     */
    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() {
        UserDto user = userFacade.getCurrentUser();
        String token = userAuthProvider.createToken(user);
        return ResponseEntity.ok(token);
    }

    /**
     * Method for requesting a password reset. If the emailDTO is valid, a password reset email is sent to the user.
     * @return response entity
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailDto emailDto) {
        userFacade.forgotPassword(emailDto);
        return ResponseEntity.ok(null);
    }

    /**
     * Method for resetting a user's password. If the passwordResetDTO is valid, the user's password is reset.
     * @param passwordResetDto password reset DTO
     * @return user DTO
     */
    @PostMapping("/reset-password")
    public ResponseEntity<UserDto> resetPassword(@Valid @RequestBody PasswordResetDto passwordResetDto) {
        UserDto user = userFacade.resetPassword(passwordResetDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }
}
