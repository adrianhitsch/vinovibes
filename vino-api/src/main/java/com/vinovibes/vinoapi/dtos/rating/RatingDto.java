package com.vinovibes.vinoapi.dtos.rating;

import com.vinovibes.vinoapi.dtos.user.RatingUserDto;
import com.vinovibes.vinoapi.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for rating.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto {

    private Long id;
    private double value;
    private String userComment;
    private String vintage;
    private double price;
    private PriceType priceType;
    private RatingUserDto user;
    private Long wineId;
}
