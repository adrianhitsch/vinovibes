package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.config.UserAuthProvider;
import com.vinovibes.vinoapi.dtos.CredentialsDto;
import com.vinovibes.vinoapi.dtos.EmailDto;
import com.vinovibes.vinoapi.dtos.PasswordResetDto;
import com.vinovibes.vinoapi.dtos.SignUpDto;
import com.vinovibes.vinoapi.dtos.UserDto;
import com.vinovibes.vinoapi.dtos.VerificationDto;
import com.vinovibes.vinoapi.facades.UserFacade;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserFacade userFacade;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody CredentialsDto credentialsDto) {
        UserDto user = userFacade.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody SignUpDto signUpDto) {
        UserDto user = userFacade.register(signUpDto);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(null);
    }

    @PostMapping("/register/otp")
    public ResponseEntity<UserDto> verifyOTP(@Valid @RequestBody VerificationDto verificationDto) {
        UserDto user = userFacade.verifyOTP(verificationDto);
        user.setToken(userAuthProvider.createToken(user)); // logs user in
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/new-otp")
    public ResponseEntity<Void> requestNewOTP(@Valid @RequestBody EmailDto emailDto) {
        userFacade.requestNewOTP(emailDto);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() {
        UserDto user = userFacade.getCurrentUser();
        String token = userAuthProvider.createToken(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailDto emailDto) {
        userFacade.forgotPassword(emailDto);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<UserDto> resetPassword(@Valid @RequestBody PasswordResetDto passwordResetDto) {
        UserDto user = userFacade.resetPassword(passwordResetDto);
        user.setToken(userAuthProvider.createToken(user)); // logs user in
        return ResponseEntity.ok(user);
    }
}
