package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.dtos.SignUpDto;
import com.vinovibes.vinoapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    @PostMapping("/generateUser")
    public ResponseEntity<String> generateUser() {
        boolean eighteen = true;
        boolean privacy = true;
        SignUpDto user = new SignUpDto("John", "Doe", "john", "doe", "doe", eighteen, privacy);
        userService.register(user);
        return ResponseEntity.ok("User Generated!");
    }
}
