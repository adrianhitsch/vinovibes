package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.enums.WineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {
    Page<Wine> findByType(WineType type, Pageable pageable);
}
