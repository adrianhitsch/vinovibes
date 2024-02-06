package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.dtos.rating.CreateRatingDto;
import com.vinovibes.vinoapi.dtos.rating.RatingDto;
import com.vinovibes.vinoapi.facades.RatingFacade;
import jakarta.validation.Valid;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingFacade ratingFacade;

    @PostMapping("/create")
    public ResponseEntity<RatingDto> createRating(@Valid @RequestBody CreateRatingDto createRatingDto) {
        if (createRatingDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(ratingFacade.createRating(createRatingDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArrayList<RatingDto>> getRatingsByWineId(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(ratingFacade.getRatingsByWineId(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("Id is required");
        }
        ratingFacade.deleteRating(id);
        return ResponseEntity.ok("Rating deleted");
    }
}
