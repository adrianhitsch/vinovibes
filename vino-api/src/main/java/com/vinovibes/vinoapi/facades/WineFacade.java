package com.vinovibes.vinoapi.facades;

import com.vinovibes.vinoapi.dtos.wine.CreateWineDto;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.mappers.WineMapper;
import com.vinovibes.vinoapi.services.UserService;
import com.vinovibes.vinoapi.services.WineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WineFacade {

    private final WineService wineService;
    private final WineMapper wineMapper;
    private final UserService userService;

    public void createWine(CreateWineDto wineDto) {
        Wine wine = wineMapper.toWineFromCreateWineDto(wineDto);
        wine.setUser(userService.getCurrentUser());
        wineService.createWine(wine);
    }
}
