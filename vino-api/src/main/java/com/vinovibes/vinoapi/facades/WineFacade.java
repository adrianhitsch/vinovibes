package com.vinovibes.vinoapi.facades;

import com.vinovibes.vinoapi.dtos.wine.CreateWineDto;
import com.vinovibes.vinoapi.dtos.wine.WineDto;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.mappers.WineMapper;
import com.vinovibes.vinoapi.services.UserService;
import com.vinovibes.vinoapi.services.WineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Facade for wine.
 */
@Service
@RequiredArgsConstructor
public class WineFacade {

    private final WineService wineService;
    private final WineMapper wineMapper;
    private final UserService userService;

    /**
     * Method for creating a wine. Sets the creator id to the current user id.
     * Maps the createWineDTO to a wine entity.
     * @param wineDto createWineDTO
     * @return wineDTO
     */
    public WineDto createWine(CreateWineDto wineDto) {
        Wine wine = wineMapper.toWineFromCreateWineDto(wineDto);
        wine.setCreatorId(userService.getCurrentUser().getId());
        wine = wineService.createWine(wine);
        return wineMapper.toWineDto(wine);
    }
}
