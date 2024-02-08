package com.vinovibes.vinoapi.dtos.wine;

import com.vinovibes.vinoapi.enums.WineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for wine.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WineDto {

    private Long id;
    private String name;
    private String country;
    private String region;
    private Integer vintage;
    private Double rating;
    private Double restaurantPrice;
    private Double storePrice;
    private String description;
    private String producer;
    private WineType type;
    private String imageUrl;
}
