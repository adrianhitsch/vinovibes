package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.enums.WineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for wines.
 */
public interface WineRepository extends JpaRepository<Wine, Long> {
    /**
     * Method for finding a wine by type and filter by pageable.
     * @param type type
     * @param pageable pageable
     * @return wine
     */
    Page<Wine> findByType(WineType type, Pageable pageable);
}
