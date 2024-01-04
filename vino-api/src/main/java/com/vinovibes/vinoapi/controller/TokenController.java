package com.vinovibes.vinoapi.controller;

import java.io.Serial;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.CookieSerializer.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class TokenController {

    @Autowired
    JwtEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {

        Authentication authentication = authenticate(loginDTO.getUsername(), loginDTO.getPassword());

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .claim("iat", Instant.now().getEpochSecond())
                .claim("exp", Instant.now().plusSeconds(60 * 60).getEpochSecond())
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(claimsSet);

        Jwt jwt = encoder.encode(parameters);

        // Create a cookie
        Cookie jwtCookie = new Cookie("jwt", jwt.getTokenValue());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // Set this to true if you're using HTTPS
        jwtCookie.setPath("/");

        String cookieValue = String.format("jwt=%s; HttpOnly;  Path=/; SameSite=Lax", jwt.getTokenValue());
        response.setHeader("Set-Cookie", cookieValue);

        // Add the cookie to the response
        response.addCookie(jwtCookie);

        // Return a 200 OK response
        return ResponseEntity.ok().build();

    }

    private Authentication authenticate(String username, String password) {
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    public static class LoginDTO {

        private String username;
        private String password;

        public LoginDTO() {
        }

        public LoginDTO(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

    }

}
