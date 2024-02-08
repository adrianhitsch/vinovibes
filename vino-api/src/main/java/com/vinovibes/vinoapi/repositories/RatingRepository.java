package com.vinovibes.vinoapi.repositories;

import com.vinovibes.vinoapi.entities.rating.Rating;
import com.vinovibes.vinoapi.enums.PriceType;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for ratings.
 * JpaRepository is a JPA specific extension of Repository.
 * It contains the full API of CrudRepository and PagingAndSortingRepository.
 * It works with JPA entities and provides methods for performing standard CRUD operations.
 * Furthermore, it allows defining custom queries through JPA's @Query annotation.
 * Example method:
 * boolean existsByUserIdAndWineId(Long userId, Long wineId) performs the query "SELECT COUNT(*) FROM rating WHERE user_id = ?1 AND wine_id = ?2"
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Method for checking if a rating exists by user id and wine id.
     * @param userId user id
     * @param wineId wine id
     * @return boolean
     */
    boolean existsByUserIdAndWineId(Long userId, Long wineId);

    /**
     * Method for finding all ratings by wine id.
     * @param id wine id
     * @return list of ratings
     */
    ArrayList<Rating> findAllByWineId(Long id);

    /**
     * Method for getting the sum of all values by wine id.
     * @param wineId wineId
     * @return int sum
     */
    @Query("SELECT SUM(r.value) FROM Rating r WHERE r.wineId = ?1")
    int sumAllValueByWineId(Long wineId);

    /**
     * Method for getting the count of all ratings by wine id.
     * @param wineId wineId
     * @return int count
     */
    int countAllByWineId(Long wineId);

    /**
     * Method for getting the count of all ratings by wine id and price type.
     * @param wineId wineId
     * @param priceType priceType
     * @return int count
     */
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.wineId = ?1 AND r.priceType = ?2")
    int countAllByWineIdAndPriceType(Long wineId, PriceType priceType);

    /**
     * Method for getting the sum of all prices by wine id and price type.
     * @param wineId wineId
     * @param priceType priceType
     * @return double sum
     */
    @Query("SELECT SUM(r.price) FROM Rating r WHERE r.wineId = ?1 AND r.priceType = ?2")
    double sumAllByWineIdAndPriceType(Long wineId, PriceType priceType);
}
