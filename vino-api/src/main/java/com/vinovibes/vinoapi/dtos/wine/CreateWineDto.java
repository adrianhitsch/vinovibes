package com.vinovibes.vinoapi.dtos.wine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating wine.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateWineDto {

    private String name;
    private String country;
    private String region;
    private String description;
    private String producer;
    private Integer vintage;
    private String type;
}
