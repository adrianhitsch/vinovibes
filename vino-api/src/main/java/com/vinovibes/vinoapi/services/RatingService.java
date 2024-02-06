package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.entities.rating.Rating;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.repositories.RatingRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public ArrayList<Rating> getRatingsByWineId(Long id) {
        return ratingRepository.findAllByWineId(id);
    }

    public boolean isRatingExists(Rating rating) {
        return ratingRepository.existsByUserIdAndWineId(rating.getUserId(), rating.getWineId());
    }

    public void deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new AppException("Rating not found", HttpStatus.NOT_FOUND);
        }
        ratingRepository.deleteById(id);
    }

    public Rating getRatingById(Long id) {
        return ratingRepository
            .findById(id)
            .orElseThrow(() -> new AppException("Rating not found", HttpStatus.NOT_FOUND));
    }

    public int getRatingCount(Long id) {
        return ratingRepository.countAllByWineId(id);
    }

    public int getRatingSum(Long wineId) {
        return ratingRepository.sumAllValueByWineId(wineId);
    }
}
