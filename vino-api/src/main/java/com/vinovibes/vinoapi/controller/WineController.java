package com.vinovibes.vinoapi.controller;

import com.vinovibes.vinoapi.dtos.NewWineDto;
import com.vinovibes.vinoapi.dtos.WineDto;
import com.vinovibes.vinoapi.services.WineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wine")
public class WineController {

    private final WineService wineService;

    private final List<String> possibleNames = Arrays.asList("Schloss Riesling", "Burgunder Traum", "Goldenes Tal Cabernet", "Sonnenaufgang Merlot", "Eichenhain Sauvignon");
    private final List<Double> possibleRatings = Arrays.asList(4.2, 4.5, 4.8, 5.0, 3.9);
    private final List<String> possibleDescriptions = Arrays.asList(
            "Ein eleganter Riesling mit fruchtigen Aromen von Aprikose und einer lebendigen Säure.",
            "Ein charmanter Burgunder mit Noten von Kirsche und einem Hauch von Vanille.",
            "Ein kräftiger Cabernet Sauvignon mit Nuancen von dunklen Beeren und einer würzigen Note.",
            "Ein samtiger Merlot mit einem Bouquet von roten Früchten und einem Hauch von Schokolade.",
            "Ein erfrischender Sauvignon Blanc mit tropischen Fruchtaromen und einer lebhaften Zitrusnote."
    );
    private final List<String> possibleProducers = Arrays.asList("Weingut Sonnenberg", "Kellermeister & Söhne", "Edelwinzerhof", "Hochland-Weine", "Gutshof am Berg");
    private final List<Integer> possibleVintages = Arrays.asList(2018, 2019, 2020, 2021, 2022);


    @GetMapping("/{id}")
    public ResponseEntity<WineDto> getWineById(@PathVariable Long id) {
        WineDto wine = wineService.getWineById(id);
        return ResponseEntity.ok(wine);
    }

    @GetMapping("/wines")
    public ResponseEntity<List<WineDto>> getWines( @RequestParam(defaultValue = "0") int skip,
                                                   @RequestParam(defaultValue = "10") int take,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @RequestParam(defaultValue = "asc") String sortDirection) {
        List<WineDto> wines = wineService.getWines(skip,take,sortBy,sortDirection);
        return ResponseEntity.ok(wines);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWine(@PathVariable Long id, @RequestBody WineDto wineDto) {
        wineService.updateWine(id, wineDto);
        return ResponseEntity.ok("Works");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWine(@RequestBody NewWineDto wineDto) {
        wineService.createWine(wineDto);
        return ResponseEntity.ok("Works");
    }

    @PostMapping("/generateWines/{quantitiy}")
    public ResponseEntity<String> generateWine(@PathVariable Integer quantitiy) {
        Random random = new Random();

        for (int i = 0; i < quantitiy && i < 10; i++) {
            NewWineDto wine = NewWineDto.builder()
                    .name(possibleNames.get(random.nextInt(possibleNames.size())))
                    .rating(possibleRatings.get(random.nextInt(possibleRatings.size())))
                    .description(possibleDescriptions.get(random.nextInt(possibleDescriptions.size())))
                    .producer(possibleProducers.get(random.nextInt(possibleProducers.size())))
                    .vintage(possibleVintages.get(random.nextInt(possibleVintages.size())))
                    .build();

            wineService.createWine(wine);
        }
        return ResponseEntity.ok("Wines Generated!");
    }

}
