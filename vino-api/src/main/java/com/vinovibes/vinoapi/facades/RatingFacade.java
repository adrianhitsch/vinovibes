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

@Service
@RequiredArgsConstructor
public class RatingFacade {

    private final RatingService ratingService;
    private final RatingMapper ratingMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final WineService wineService;

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

    public void deleteRating(Long id) {
        User user = userService.getCurrentUser();
        Rating rating = ratingService.getRatingById(id);
        if (!rating.getUserId().equals(user.getId())) {
            throw new AppException("You are not authorized to delete this rating", HttpStatus.UNAUTHORIZED);
        }
        ratingService.deleteRating(id);
    }

    public void updateWineRating(RatingDto ratingDto) {
        double newRating = calculateNewRating(ratingDto);
        wineService.updateWineRating(ratingDto.getWineId(), newRating);
    }

    private double calculateNewRating(RatingDto ratingDto) {
        int ratingCount = ratingService.getRatingCount(ratingDto.getWineId());
        double ratingSum = ratingService.getRatingSum(ratingDto.getWineId());
        return ratingSum / ratingCount;
    }

    private void updateWinePrice(RatingDto ratingDto) {
        double newPrice = calculateNewPrice(ratingDto);
        wineService.updateWinePrice(ratingDto.getWineId(), newPrice, ratingDto.getPriceType());

    }

    private double calculateNewPrice(RatingDto ratingDto) {
        int priceCount = ratingService.getPriceCount(ratingDto.getWineId(), ratingDto.getPriceType());
        double priceSum = ratingService.getPriceSum(ratingDto.getWineId(), ratingDto.getPriceType());
        return priceSum / priceCount;
    }


}
