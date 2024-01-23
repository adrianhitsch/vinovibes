package com.vinovibes.vinoapi.dtos.wine;

import com.vinovibes.vinoapi.enums.WineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateWineDto {

    private String name;
    private double rating;
    private String country;
    private String region;
    private String description;
    private String producer;
    private Integer vintage;
    private String type;
}
