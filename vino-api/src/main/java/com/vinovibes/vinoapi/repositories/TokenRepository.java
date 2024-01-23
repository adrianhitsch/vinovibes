package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.user.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String value);
}
