package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.wine.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {}
