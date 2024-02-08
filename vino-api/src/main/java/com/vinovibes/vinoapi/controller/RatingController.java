package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.dtos.rating.CreateRatingDto;
import com.vinovibes.vinoapi.dtos.rating.RatingDto;
import com.vinovibes.vinoapi.facades.RatingFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for ratings.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingFacade ratingFacade;

    /**
     * Method for creating a rating. If the createRatingDTO is valid, the rating is created.
     * @param createRatingDto createRatingDTO
     * @return ratingDTO in response entity
     */
    @Operation(summary = "Create a rating", description = "Creates a rating if the CreateRatingDTO is valid.")
    @ApiResponse(
        responseCode = "200",
        description = "Rating created",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RatingDto.class)) }
    )
    @PostMapping("/create")
    public ResponseEntity<RatingDto> createRating(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Create rating DTO"
        ) @Valid @RequestBody CreateRatingDto createRatingDto
    ) {
        if (createRatingDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(ratingFacade.createRating(createRatingDto));
    }

    /**
     * Method for getting all ratings by wine id.
     * @return list of ratingDTOs in response entity
     */
    @Operation(
        summary = "Get all ratings by wine id",
        description = "Retrieves all ratings associated with a specific wine id."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Ratings retrieved",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ArrayList.class)) }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ArrayList<RatingDto>> getRatingsByWineId(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(ratingFacade.getRatingsByWineId(id));
    }

    /**
     * Method for deleting a rating by id.
     * @param id id
     * @return response entity with message
     */
    @Operation(summary = "Delete a rating", description = "Deletes a rating by its id.")
    @ApiResponse(responseCode = "200", description = "Rating deleted", content = { @Content(mediaType = "text/plain") })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("Id is required");
        }
        ratingFacade.deleteRating(id);
        return ResponseEntity.ok("Rating deleted");
    }
}
