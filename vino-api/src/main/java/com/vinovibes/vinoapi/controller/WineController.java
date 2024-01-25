package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.dtos.wine.CreateWineDto;
import com.vinovibes.vinoapi.dtos.wine.WineDto;
import com.vinovibes.vinoapi.dtos.wine.WineFilterDto;
import com.vinovibes.vinoapi.facades.WineFacade;
import com.vinovibes.vinoapi.services.WineService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wine")
public class WineController {

    private final WineService wineService;
    private final WineFacade wineFacade;

    @GetMapping("/{id}")
    public ResponseEntity<WineDto> getWineById(@PathVariable Long id) {
        WineDto wine = wineService.getWineById(id);
        return ResponseEntity.ok(wine);
    }

    @GetMapping("/wines")
    public ResponseEntity<List<WineDto>> getWinesByType(@RequestBody WineFilterDto wineFilterDto) {
        List<WineDto> wines = wineService.getWines(wineFilterDto);
        return ResponseEntity.ok(wines);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWine(@PathVariable Long id, @RequestBody WineDto wineDto) {
        wineService.updateWine(id, wineDto);
        return ResponseEntity.ok("Works");
    }

    @PostMapping("/create")
    public ResponseEntity<WineDto> createWine(@RequestBody CreateWineDto wineDto) {
        WineDto wine = wineFacade.createWine(wineDto);
        return ResponseEntity.ok(wine);
    }
}
