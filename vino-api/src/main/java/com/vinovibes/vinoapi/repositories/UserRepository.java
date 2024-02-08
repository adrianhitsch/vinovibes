package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.user.Token;
import com.vinovibes.vinoapi.entities.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for users.
 * JpaRepository is a JPA specific extension of Repository.
 * It contains the full API of CrudRepository and PagingAndSortingRepository.
 * It works with JPA entities and provides methods for performing standard CRUD operations.
 * Furthermore, it allows defining custom queries through JPA's @Query annotation.
 * Example method:
 * Optional<User> findByEmail(String email) performs the query "SELECT * FROM user WHERE email = ?1"
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Method for finding a user by email.
     * @param email email
     * @return user
     */
    Optional<User> findByEmail(String email);

    /**
     * Method for finding a user by token.
     * @param token token
     * @return user
     */
    Optional<User> findByToken(Token token);
}
