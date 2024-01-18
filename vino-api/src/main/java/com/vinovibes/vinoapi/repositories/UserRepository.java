package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.Token;
import com.vinovibes.vinoapi.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByToken(Token token);
}
