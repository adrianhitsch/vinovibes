package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.entities.Token;
import com.vinovibes.vinoapi.repositories.TokenRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public Token generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String value = base64Encoder.encodeToString(randomBytes);

        Token token = new Token();
        token.setValue(value);
        token.setExpiryTime(LocalDateTime.now().plusHours(1));
        return token;
    }

    public Optional<Token> getTokenByValue(String tokenValue) {
        return tokenRepository.findByValue(tokenValue);
    }
}
