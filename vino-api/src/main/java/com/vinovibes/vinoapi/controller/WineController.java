package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.dtos.wine.CreateWineDto;
import com.vinovibes.vinoapi.dtos.wine.WineDto;
import com.vinovibes.vinoapi.dtos.wine.WineFilterDto;
import com.vinovibes.vinoapi.facades.WineFacade;
import com.vinovibes.vinoapi.services.WineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for wines.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wine")
public class WineController {

    private final WineService wineService;
    private final WineFacade wineFacade;

    /**
     * Method for getting a wine by id. If the id is valid, the wine is returned.
     * @param id id
     * @return wineDTO in response entity
     */
    @Operation(summary = "Get a wine by id", description = "Retrieves a wine by its id.")
    @ApiResponse(
        responseCode = "200",
        description = "Wine retrieved",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WineDto.class)) }
    )
    @GetMapping("/{id}")
    public ResponseEntity<WineDto> getWineById(@PathVariable Long id) {
        WineDto wine = wineService.getWineDtoById(id);
        return ResponseEntity.ok(wine);
    }

    /**
     * Method for getting all wines. If the wineFilterDTO is valid, the wines are returned.
     * Skip and take are used for pagination.
     * Sort by and sort direction are used for sorting.
     * Type is used for filtering by type.
     * @param skip skip
     * @param take take
     * @param sortBy sort by
     * @param sortDirection sort direction
     * @param type type
     * @return list of wineDTOs in response entity
     */
    @Operation(
        summary = "Get all wines",
        description = "Retrieves a list of wines, with optional filters and pagination."
    )
    @ApiResponse(
        responseCode = "200",
        description = "List of wines retrieved",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }
    )
    @GetMapping("/wines")
    public ResponseEntity<List<WineDto>> getWines(
        @Parameter(description = "Number of records to skip for pagination") @Valid @RequestParam int skip,
        @Parameter(description = "Number of records to take for pagination") @Valid @RequestParam int take,
        @Parameter(description = "Parameter to sort by") @Valid @RequestParam String sortBy,
        @Parameter(description = "Direction to sort (asc/desc)") @Valid @RequestParam String sortDirection,
        @Parameter(description = "Type of wine to filter by") @Valid @RequestParam String type
    ) {
        WineFilterDto wineFilterDto = new WineFilterDto(skip, take, sortBy, sortDirection, type);
        List<WineDto> wines = wineService.getWines(wineFilterDto);
        return ResponseEntity.ok(wines);
    }

    /**
     * Method for updating a wine. If the id and wineDTO are valid, the wine is updated.
     * @param id id
     * @param wineDto wineDTO
     * @return response entity with message
     */
    @Operation(summary = "Update a wine", description = "Updates a wine's information by id.")
    @ApiResponse(responseCode = "200", description = "Wine updated", content = { @Content(mediaType = "text/plain") })
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWine(
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Wine information"
        ) @RequestBody WineDto wineDto
    ) {
        wineService.updateWine(id, wineDto);
        return ResponseEntity.ok("Wine with id " + id + " updated");
    }

    /**
     * Method for creating a wine. If the createWineDTO is valid, the wine is created.
     * @param wineDto createWineDTO
     * @return wineDTO in response entity
     */
    @Operation(summary = "Create a wine", description = "Creates a new wine record.")
    @ApiResponse(
        responseCode = "200",
        description = "Wine created",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WineDto.class)) }
    )
    @PostMapping("/create")
    public ResponseEntity<WineDto> createWine(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Create wine DTO"
        ) @RequestBody CreateWineDto wineDto
    ) {
        WineDto wine = wineFacade.createWine(wineDto);
        return ResponseEntity.ok(wine);
    }
}
