package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.dtos.wine.WineDto;
import com.vinovibes.vinoapi.dtos.wine.WineFilterDto;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.enums.WineType;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.exceptions.WineNotFoundException;
import com.vinovibes.vinoapi.mappers.WineMapper;
import com.vinovibes.vinoapi.repositories.WineRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WineService {

    private final WineRepository wineRepository;
    private final WineMapper wineMapper;

    public WineDto getWineById(Long id) {
        Wine wine = wineRepository
            .findById(id)
            .orElseThrow(() -> new AppException("Unknown wine", HttpStatus.NOT_FOUND));
        return wineMapper.toWineDto(wine);
    }

    public Wine createWine(Wine wine) {
        return wineRepository.save(wine);
    }

    public List<WineDto> getWines(WineFilterDto wineFilterDto) {
        Sort.Direction direction = "asc".equalsIgnoreCase(wineFilterDto.sortDirection())
            ? Sort.Direction.ASC
            : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(
            wineFilterDto.skip(),
            wineFilterDto.take(),
            Sort.by(direction, wineFilterDto.sortBy())
        );
        List<Wine> wines;

        if (wineFilterDto.type() != null && !wineFilterDto.type().isEmpty()) {
            WineType wineType = WineType.valueOf(wineFilterDto.type());
            wines = wineRepository.findByType(wineType, pageable).getContent();
        } else {
            wines = wineRepository.findAll(pageable).getContent();
        }

        List<WineDto> wineDtos = new ArrayList<>();

        for (Wine wine : wines) {
            wineDtos.add(wineMapper.toWineDto(wine));
        }

        return wineDtos;
    }

    public void updateWine(Long id, WineDto wineDto) {
        Optional<Wine> existingWineOptional = wineRepository.findById(id);

        if (existingWineOptional.isPresent()) {
            Wine wine = getExistingWine(wineDto, existingWineOptional);
            wine.onUpdate();
            wineRepository.save(wine);
        } else {
            throw new WineNotFoundException("Wine with ID " + id + " not found");
        }
    }

    private static Wine getExistingWine(WineDto wineDto, Optional<Wine> existingWineOptional) {
        if (existingWineOptional.isPresent()) {
            Wine existingWine = existingWineOptional.get();
            if (wineDto.getName() != null) {
                existingWine.setName(wineDto.getName());
            }
            if (wineDto.getCountry() != null) {
                existingWine.setCountry(wineDto.getCountry());
            }
            if (wineDto.getRegion() != null) {
                existingWine.setRegion(wineDto.getRegion());
            }
            if (wineDto.getRating() != 0) {
                existingWine.setRating(wineDto.getRating());
            }
            if (wineDto.getDescription() != null) {
                existingWine.setDescription(wineDto.getDescription());
            }
            if (wineDto.getProducer() != null) {
                existingWine.setProducer(wineDto.getProducer());
            }
            return existingWine;
        } else {
            throw new WineNotFoundException("Wine with ID " + wineDto.getId() + " not found");
        }
    }
}
