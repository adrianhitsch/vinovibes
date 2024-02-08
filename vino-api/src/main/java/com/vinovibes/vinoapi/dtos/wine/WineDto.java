package com.vinovibes.vinoapi.dtos.wine;

import com.vinovibes.vinoapi.enums.WineType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Schema(description = "Wine ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    @Schema(description = "Wine name", example = "Bijou", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String name;

    @Schema(description = "Wine country", example = "France", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String country;

    @Schema(description = "Wine region", example = "Bordeaux", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String region;

    @Schema(description = "Wine vintage", example = "2015", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer vintage;

    @Schema(description = "Wine rating", example = "4.5", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double rating;

    @Schema(
        description = "Wine price in restaurant",
        example = "100.0",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Double restaurantPrice;

    @Schema(description = "Wine price in store", example = "80.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double storePrice;

    @Schema(
        description = "Wine description",
        example = "Dark red, full-bodied, with a rich, complex palate and a long finish.",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private String description;

    @Schema(description = "Wine producer", example = "Château Margaux", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String producer;

    @Schema(description = "Wine category", example = "RED", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private WineType type;
}
