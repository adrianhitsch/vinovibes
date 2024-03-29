package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.entities.user.Token;
import com.vinovibes.vinoapi.repositories.TokenRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for tokens.
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    /**
     * Secure random number generator.
     */
    private static final SecureRandom secureRandom = new SecureRandom();
    /**
     * Base64 encoder.
     */
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    /**
     * Method for generating a new token. The token is a 24 byte random value encoded in base64.
     * @return token
     */
    public Token generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String value = base64Encoder.encodeToString(randomBytes);

        Token token = new Token();
        token.setValue(value);
        token.setExpiryTime(LocalDateTime.now().plusHours(1));
        return token;
    }

    /**
     * Method for saving a token.
     * @param tokenValue token
     * @return token
     */
    public Optional<Token> getTokenByValue(String tokenValue) {
        return tokenRepository.findByValue(tokenValue);
    }

    /**
     * Checks if a token is expired.
     * @param token token
     * @return token
     */
    public boolean isTokenExpired(Token token) {
        return LocalDateTime.now().isAfter(token.getExpiryTime());
    }
}
