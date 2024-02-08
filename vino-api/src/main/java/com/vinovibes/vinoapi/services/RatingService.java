package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.dtos.rating.RatingDto;
import com.vinovibes.vinoapi.entities.rating.Rating;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.enums.PriceType;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.repositories.RatingRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service for ratings.
 */
@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    /**
     * Method for creating a rating.
     * @param rating rating
     * @return rating
     */
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Method for getting all ratings by wine id.
     * @param id id
     * @return list of ratings
     */
    public ArrayList<Rating> getRatingsByWineId(Long id) {
        return ratingRepository.findAllByWineId(id);
    }

    /**
     * Method for checking if a rating exists.
     * @param rating rating
     * @return boolean
     */
    public boolean isRatingExists(Rating rating) {
        return ratingRepository.existsByUserIdAndWineId(rating.getUserId(), rating.getWineId());
    }

    /**
     * Method for deleting a rating by id. If the rating does not exist, an exception is thrown.
     * @param id id
     */
    public void deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new AppException("Rating not found", HttpStatus.NOT_FOUND);
        }
        ratingRepository.deleteById(id);
    }

    /**
     * Method for getting a rating by id.
     * @param id id
     * @return rating
     */
    public Rating getRatingById(Long id) {
        return ratingRepository
            .findById(id)
            .orElseThrow(() -> new AppException("Rating not found", HttpStatus.NOT_FOUND));
    }

    /**
     * Method for getting the count of all ratings by wine id.
     * @param id id
     * @return int count
     */
    public int getRatingCount(Long id) {
        return ratingRepository.countAllByWineId(id);
    }

    /**
     * Method for getting the sum of all values by wine id.
     * @param wineId wineId
     * @return int sum
     */
    public int getRatingSum(Long wineId) {
        return ratingRepository.sumAllValueByWineId(wineId);
    }

    /**
     * Method for getting the count of all ratings by wine id and price type.
     * @param wineId wineId
     * @param priceType priceType
     * @return int count
     */
    public int getPriceCount(Long wineId, PriceType priceType) {
        return ratingRepository.countAllByWineIdAndPriceType(wineId, priceType);
    }

    /**
     * Method for getting the sum of all prices by wine id and price type.
     * @param wineId wineId
     * @param priceType priceType
     * @return double sum
     */
    public double getPriceSum(Long wineId, PriceType priceType) {
        return ratingRepository.sumAllByWineIdAndPriceType(wineId, priceType);
    }

    /**
     * Method for calculating a new rating. Gets the rating count and the rating sum.
     * Returns the new rating.
     * @param ratingDto ratingDTO
     * @return new rating
     */
    public double calculateNewRating(RatingDto ratingDto) {
        int ratingCount = getRatingCount(ratingDto.getWineId());
        double ratingSum = getRatingSum(ratingDto.getWineId());
        return ratingSum / ratingCount;
    }

    /**
     * Method for calculating a new price. Gets the price count and the price sum.
     * Returns the new price.
     * @param ratingDto ratingDTO
     * @return new price
     */
    public double calculateNewPrice(RatingDto ratingDto) {
        int priceCount = getPriceCount(ratingDto.getWineId(), ratingDto.getPriceType());
        double priceSum = getPriceSum(ratingDto.getWineId(), ratingDto.getPriceType());
        return priceSum / priceCount;
    }
}
