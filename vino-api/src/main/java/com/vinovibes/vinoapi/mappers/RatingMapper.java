package com.vinovibes.vinoapi.mappers;

import com.vinovibes.vinoapi.dtos.rating.CreateRatingDto;
import com.vinovibes.vinoapi.dtos.rating.RatingDto;
import com.vinovibes.vinoapi.entities.rating.Rating;
import com.vinovibes.vinoapi.enums.PriceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "priceType", source = "priceType", qualifiedByName = "stringToPriceType")
    Rating toRatingFromCreateRatingDto(CreateRatingDto createRatingDto);

    @Mapping(target = "user", ignore = true)
    RatingDto toRatingDto(Rating rating);

    @Named("stringToPriceType")
    default PriceType mapStringToPriceType(String priceType) {
        return priceType != null ? PriceType.valueOf(priceType) : null;
    }
}
