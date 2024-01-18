package com.vinovibes.vinoapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewWineDto {

        private String name;
        private double rating;
        private String country;
        private String region;
        private double restaurantPrice;
        private double storePrice;
        private String description;
        private String producer;
        private int vintage;
}
