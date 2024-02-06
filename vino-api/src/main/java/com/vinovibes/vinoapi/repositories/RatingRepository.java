package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.rating.Rating;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByUserIdAndWineId(Long userId, Long wineId);

    ArrayList<Rating> findAllByWineId(Long id);

    @Query("SELECT SUM(r.value) FROM Rating r WHERE r.wineId = ?1")
    int sumAllValueByWineId(Long wineId);

    int countAllByWineId(Long wineId);
}
