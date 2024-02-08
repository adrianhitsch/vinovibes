package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.config.UserAuthProvider;
import com.vinovibes.vinoapi.dtos.user.CredentialsDto;
import com.vinovibes.vinoapi.dtos.user.EmailDto;
import com.vinovibes.vinoapi.dtos.user.PasswordResetDto;
import com.vinovibes.vinoapi.dtos.user.SignUpDto;
import com.vinovibes.vinoapi.dtos.user.UserDto;
import com.vinovibes.vinoapi.dtos.user.VerificationDto;
import com.vinovibes.vinoapi.facades.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
        summary = "Log in a user",
        description = "Logs in a user and returns a JWT token if the credentials are valid."
    )
    @ApiResponse(
        responseCode = "200",
        description = "User logged in",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }
    )
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credentials for login"
        ) @Valid @RequestBody CredentialsDto credentialsDto
    ) {
        UserDto user = userFacade.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    /**
     * Method for registering a new user. If the sign up DTO is valid, the user is registered.
     * @param signUpDto sign up DTO
     * @return user DTO
     */
    @Operation(summary = "Register a new user", description = "Registers a new user if the sign up DTO is valid.")
    @ApiResponse(
        responseCode = "201",
        description = "User registered",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Signup information"
        ) @Valid @RequestBody SignUpDto signUpDto
    ) {
        UserDto user = userFacade.register(signUpDto);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(null);
    }

    /**
     * Method for verifying an One Time Password. If the verification DTO is valid, the user is verified and logged in.
     * @param verificationDto verification DTO
     * @return user DTO
     */
    @Operation(
        summary = "Verify One Time Password",
        description = "Verifies an OTP and logs the user in if the verification DTO is valid."
    )
    @ApiResponse(
        responseCode = "200",
        description = "OTP verified, user logged in",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }
    )
    @PostMapping("/register/otp")
    public ResponseEntity<UserDto> verifyOTP(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "OTP verification information"
        ) @Valid @RequestBody VerificationDto verificationDto
    ) {
        UserDto user = userFacade.verifyOTP(verificationDto);
        user.setToken(userAuthProvider.createToken(user)); // logs user in
        return ResponseEntity.ok(user);
    }

    /**
     * Method for requesting a new One Time Password. If the email DTO is valid, a new OTP is sent to the user.
     * @param emailDto email DTO
     * @return response entity
     */
    @Operation(
        summary = "Request a new OTP",
        description = "Requests a new OTP to be sent to the user if the email is valid."
    )
    @ApiResponse(responseCode = "200", description = "New OTP requested")
    @PostMapping("/register/new-otp")
    public ResponseEntity<Void> requestNewOTP(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Email for OTP request"
        ) @Valid @RequestBody EmailDto emailDto
    ) {
        userFacade.requestNewOTP(emailDto);
        return ResponseEntity.ok(null);
    }

    /**
     * Method for refreshing the JWT token.
     * @return JWT token
     */
    @Operation(summary = "Refresh JWT token", description = "Refreshes the JWT token for the current user.")
    @ApiResponse(
        responseCode = "200",
        description = "JWT token refreshed",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }
    )
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
    @Operation(
        summary = "Request password reset",
        description = "Initiates a password reset process if the user's email is valid."
    )
    @ApiResponse(responseCode = "200", description = "Password reset initiated")
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Email for password reset"
        ) @Valid @RequestBody EmailDto emailDto
    ) {
        userFacade.forgotPassword(emailDto);
        return ResponseEntity.ok(null);
    }

    /**
     * Method for resetting a user's password. If the passwordResetDTO is valid, the user's password is reset.
     * @param passwordResetDto password reset DTO
     * @return user DTO
     */
    @Operation(
        summary = "Reset password",
        description = "Resets the user's password if the password reset DTO is valid."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Password reset",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }
    )
    @PostMapping("/reset-password")
    public ResponseEntity<UserDto> resetPassword(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Password reset information"
        ) @Valid @RequestBody PasswordResetDto passwordResetDto
    ) {
        UserDto user = userFacade.resetPassword(passwordResetDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }
}
