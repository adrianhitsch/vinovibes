package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.config.UserAuthProvider;
import com.vinovibes.vinoapi.dtos.CredentialsDto;
import com.vinovibes.vinoapi.dtos.RequestOtpDto;
import com.vinovibes.vinoapi.dtos.SignUpDto;
import com.vinovibes.vinoapi.dtos.UserDto;
import com.vinovibes.vinoapi.dtos.VerificationDto;
import com.vinovibes.vinoapi.facades.UserFacade;
import com.vinovibes.vinoapi.services.UserService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserFacade userFacade;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userFacade.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userFacade.register(signUpDto);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(null);
    }

    @PostMapping("/register/otp")
    public ResponseEntity<UserDto> verifyOTP(@RequestBody VerificationDto verificationDto) {
        UserDto user = userFacade.verifyOTP(verificationDto);
        user.setToken(userAuthProvider.createToken(user)); // logs user in
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/new-otp")
    public ResponseEntity<UserDto> requestNewOTP(@RequestBody RequestOtpDto requestOtpDto) {
        userFacade.requesNewtOTP(requestOtpDto);
        return ResponseEntity.ok(null);
    }
}
