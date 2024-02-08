package com.vinovibes.vinoapi.mappers;

import com.vinovibes.vinoapi.dtos.wine.CreateWineDto;
import com.vinovibes.vinoapi.dtos.wine.WineDto;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.enums.WineType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for wines.
 */
@Mapper(componentModel = "spring")
public interface WineMapper {
    /**
     * Maps the wine to a wineDTO.
     * @param wine wine
     * @return wineDTO
     */
    WineDto toWineDto(Wine wine);

    /**
     * Maps the createWineDTO to a wine entity. Sets the type to a wineType enum.
     * @param createWineDto createWineDTO
     * @return wine
     */
    @Mapping(target = "type", source = "type", qualifiedByName = "stringToWineType")
    Wine toWineFromCreateWineDto(CreateWineDto createWineDto);

    /**
     * Maps the wineType enum to a string.
     * @param type type
     * @return string
     */
    @Named("stringToWineType")
    default WineType mapStringToWineType(String type) {
        return type != null ? WineType.valueOf(type) : null;
    }
}
