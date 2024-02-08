package com.vinovibes.vinoapi.mappers;

import com.vinovibes.vinoapi.dtos.rating.CreateRatingDto;
import com.vinovibes.vinoapi.dtos.rating.RatingDto;
import com.vinovibes.vinoapi.entities.rating.Rating;
import com.vinovibes.vinoapi.enums.PriceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for ratings.
 */
@Mapper(componentModel = "spring")
public interface RatingMapper {
    /**
     * Maps the createRatingDTO to a rating entity. Sets the priceType to a priceType enum. Target userId is ignored.
     * @param createRatingDto createRatingDTO
     * @return rating
     */
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "priceType", source = "priceType", qualifiedByName = "stringToPriceType")
    Rating toRatingFromCreateRatingDto(CreateRatingDto createRatingDto);

    /**
     * Maps the rating to a ratingDTO. Target user is ignored.
     * @param rating rating
     * @return ratingDTO
     */
    @Mapping(target = "user", ignore = true)
    RatingDto toRatingDto(Rating rating);

    /**
     * Maps the priceType enum to a string.
     * @param priceType priceType
     * @return string
     */
    @Named("stringToPriceType")
    default PriceType mapStringToPriceType(String priceType) {
        return priceType != null ? PriceType.valueOf(priceType) : null;
    }
}
