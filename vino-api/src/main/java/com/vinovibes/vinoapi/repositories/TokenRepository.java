package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.user.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for tokens.
 * JpaRepository is a JPA specific extension of Repository.
 * It contains the full API of CrudRepository and PagingAndSortingRepository.
 * It works with JPA entities and provides methods for performing standard CRUD operations.
 * Furthermore, it allows defining custom queries through JPA's @Query annotation.
 * Example method:
 * Optional<Token> findByValue(String value) performs the query "SELECT * FROM token WHERE value = ?1"
 */
public interface TokenRepository extends JpaRepository<Token, Long> {

    /**
     * Method for finding a token by value.
     * @param value value
     * @return token
     */
    Optional<Token> findByValue(String value);
}
