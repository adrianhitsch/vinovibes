package com.vinovibes.vinoapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vinovibes.vinoapi.dtos.UserDto;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 3_600_000); // One hour
        return JWT
            .create()
            .withIssuer(userDto.getEmail())
            .withIssuedAt(now)
            .withExpiresAt(expiresAt)
            .withClaim("firstName", userDto.getFirstName())
            .withClaim("lastName", userDto.getLastName())
            .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);

        UserDto user = UserDto
            .builder()
            .email(decoded.getIssuer())
            .firstName(decoded.getClaim("firstName").asString())
            .lastName(decoded.getClaim("lastName").asString())
            .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
