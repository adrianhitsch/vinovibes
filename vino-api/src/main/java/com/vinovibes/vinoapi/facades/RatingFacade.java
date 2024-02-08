package com.vinovibes.vinoapi.facades;

import com.vinovibes.vinoapi.dtos.rating.CreateRatingDto;
import com.vinovibes.vinoapi.dtos.rating.RatingDto;
import com.vinovibes.vinoapi.dtos.user.RatingUserDto;
import com.vinovibes.vinoapi.entities.rating.Rating;
import com.vinovibes.vinoapi.entities.user.User;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.enums.PriceType;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.exceptions.WineNotFoundException;
import com.vinovibes.vinoapi.mappers.RatingMapper;
import com.vinovibes.vinoapi.mappers.UserMapper;
import com.vinovibes.vinoapi.services.RatingService;
import com.vinovibes.vinoapi.services.UserService;
import com.vinovibes.vinoapi.services.WineService;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Facade for ratings.
 */
@Service
@RequiredArgsConstructor
public class RatingFacade {

    private final RatingService ratingService;
    private final RatingMapper ratingMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final WineService wineService;

    /**
     * Method for creating a rating. Maps the createRatingDTO to a rating entity.
     * Sets the user id to the currentUser id. Checks if the rating already exists.
     * Creates the rating. Maps the rating to a ratingDTO. Maps the user to a ratingUserDTO.
     * Updates the wine rating and the wine price.
     * Returns the rating DTO.
     * @param createRatingDto createRatingDTO
     * @return ratingDTO
     */
    public RatingDto createRating(CreateRatingDto createRatingDto) {
        Rating rating = ratingMapper.toRatingFromCreateRatingDto(createRatingDto);
        User user = userService.getCurrentUser();
        if (user == null) {
            throw new AppException("Something went completely wrong.", HttpStatus.BAD_REQUEST);
        }
        rating.setUserId(user.getId());

        boolean isRatingExists = ratingService.isRatingExists(rating);
        if (isRatingExists) {
            throw new AppException("Rating already exists", HttpStatus.BAD_REQUEST);
        }

        rating = ratingService.createRating(rating);

        RatingDto ratingDto = ratingMapper.toRatingDto(rating);
        RatingUserDto ratingUserDto = userMapper.toRatingUserDto(user);
        ratingDto.setUser(ratingUserDto);

        updateWineRating(ratingDto);
        updateWinePrice(ratingDto);

        return ratingDto;
    }

    /**
     * Method for getting all ratings by wine id. Maps the ratings to ratingDTOs.
     * Maps the users to ratingUserDTOs. Returns the list of ratingDTOs.
     * @param id id
     * @return list of ratingDTOs
     */
    public ArrayList<RatingDto> getRatingsByWineId(Long id) {
        ArrayList<Rating> ratings = ratingService.getRatingsByWineId(id);
        ArrayList<RatingDto> ratingDtos = new ArrayList<>();
        for (Rating rating : ratings) {
            RatingDto ratingDto = ratingMapper.toRatingDto(rating);
            Optional<User> user = userService.getUserById(rating.getUserId());
            RatingUserDto ratingUserDto;

            if (user.isEmpty()) {
                ratingUserDto = new RatingUserDto(null, "Deleted User");
            } else {
                ratingUserDto = userMapper.toRatingUserDto(user.get());
            }

            ratingDto.setUser(ratingUserDto);
            ratingDtos.add(ratingDto);
        }
        return ratingDtos;
    }

    /**
     * Method for deleting a rating by id. Checks if the user is authorized to delete the rating.
     * Deletes the rating.
     * @param id id
     */
    public void deleteRating(Long id) {
        User user = userService.getCurrentUser();
        Rating rating = ratingService.getRatingById(id);
        if (!rating.getUserId().equals(user.getId())) {
            throw new AppException("You are not authorized to delete this rating", HttpStatus.UNAUTHORIZED);
        }
        ratingService.deleteRating(id);
    }

    /**
     * Method for updating a rating. Calculates the new rating and updates the wine rating.
     * @param ratingDto ratingDTO
     */
    private void updateWineRating(RatingDto ratingDto) {
        double newRating = ratingService.calculateNewRating(ratingDto);
        wineService.updateWineRating(ratingDto.getWineId(), newRating);
    }

    /**
     * Method for updating a wine price. Calculates the new price.
     * Updates the wine price.
     * @param ratingDto ratingDTO
     */
    private void updateWinePrice(RatingDto ratingDto) {
        double newPrice = ratingService.calculateNewPrice(ratingDto);
        wineService.updateWinePrice(ratingDto.getWineId(), newPrice, ratingDto.getPriceType());
    }
}
