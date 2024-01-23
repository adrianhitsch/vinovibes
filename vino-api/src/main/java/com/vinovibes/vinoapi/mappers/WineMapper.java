package com.vinovibes.vinoapi.mappers;

import com.vinovibes.vinoapi.dtos.wine.CreateWineDto;
import com.vinovibes.vinoapi.dtos.wine.WineDto;
import com.vinovibes.vinoapi.entities.wine.Wine;
import com.vinovibes.vinoapi.enums.WineType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WineMapper {
    WineDto toWineDto(Wine wine);

    @Mapping(target = "type", source = "type", qualifiedByName = "stringToWineType")
    @Mapping(target = "user", ignore = true)
    Wine toWineFromCreateWineDto(CreateWineDto createWineDto);

    @Named("stringToWineType")
    default WineType mapStringToWineType(String type) {
        return type != null ? WineType.valueOf(type) : null;
    }
}
