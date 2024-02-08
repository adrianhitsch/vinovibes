package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.dtos.wine.WineDto;
import com.vinovibes.vinoapi.dtos.wine.WineFilterDto;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.enums.PriceType;
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

/**
 * Service for wines.
 */
@Service
@RequiredArgsConstructor
public class WineService {

    private final WineRepository wineRepository;
    private final WineMapper wineMapper;

    /**
     * Method for getting a wine by id. If the id is valid, the wine is returned.
     * @param id id
     * @return wineDTO
     */
    public WineDto getWineDtoById(Long id) {
        Wine wine = wineRepository
            .findById(id)
            .orElseThrow(() -> new AppException("Unknown wine", HttpStatus.NOT_FOUND));
        return wineMapper.toWineDto(wine);
    }

    /**
     * Method for creating a wine.
     * @param wine wine
     * @return wine
     */
    public Wine createWine(Wine wine) {
        return wineRepository.save(wine);
    }

    /**
     * Method for getting all wines.
     * Skip and take are used for pagination.
     * Sort by and sort direction are used for sorting.
     * Type is used for filtering by type.
     * @param wineFilterDto wine filter DTO
     * @return list of wineDTOs
     */
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

    /**
     * Method for updating a wine. If the no wine is found, an exception is thrown.
     * @param id id
     * @param wineDto wineDTO
     */
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

    /*
        * Method for getting an existing wine. If the wineDTO has a value, the existing wine is updated.
     */
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

    /**
     * Method for updating a wine rating.
     * If the wine is not found, an exception is thrown.
     * @param wineId wine id
     * @param rating rating
     */
    public void updateWineRating(Long wineId, double rating) {
        Optional<Wine> existingWineOptional = wineRepository.findById(wineId);
        if (existingWineOptional.isPresent()) {
            Wine existingWine = existingWineOptional.get();
            existingWine.setRating(rating);
            wineRepository.save(existingWine);
        } else {
            throw new WineNotFoundException("Wine with ID " + wineId + " not found");
        }
    }

    /**
     * Method for updating a wine price.
     * If the wine is not found, an exception is thrown.
     * @param wineId wine id
     * @param newPrice new price
     * @param priceType price type
     */
    public void updateWinePrice(Long wineId, double newPrice, PriceType priceType) {
        Optional<Wine> existingWineOptional = wineRepository.findById(wineId);
        if (existingWineOptional.isPresent()) {
            Wine existingWine = existingWineOptional.get();
            if (priceType == PriceType.RESTAURANT) {
                existingWine.setRestaurantPrice(newPrice);
            } else {
                existingWine.setStorePrice(newPrice);
            }
            wineRepository.save(existingWine);
        } else {
            throw new WineNotFoundException("Wine with ID " + wineId + " not found");
        }
    }
}
