package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.rating.Rating;
import java.util.ArrayList;

import com.vinovibes.vinoapi.enums.PriceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByUserIdAndWineId(Long userId, Long wineId);

    ArrayList<Rating> findAllByWineId(Long id);

    @Query("SELECT SUM(r.value) FROM Rating r WHERE r.wineId = ?1")
    int sumAllValueByWineId(Long wineId);

    int countAllByWineId(Long wineId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.wineId = ?1 AND r.priceType = ?2")
    int countAllByWineIdAndPriceType(Long wineId, PriceType priceType);

    @Query("SELECT SUM(r.price) FROM Rating r WHERE r.wineId = ?1 AND r.priceType = ?2")
    double sumAllByWineIdAndPriceType(Long wineId, PriceType priceType);
}
