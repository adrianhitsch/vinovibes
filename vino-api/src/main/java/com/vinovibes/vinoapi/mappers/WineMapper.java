package com.vinovibes.vinoapi.mappers;
import com.vinovibes.vinoapi.dtos.NewWineDto;
import com.vinovibes.vinoapi.dtos.WineDto;
import com.vinovibes.vinoapi.entities.Wine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WineMapper {

    WineDto toWineDto(Wine wine);

    Wine toWineFromNewWineDto(NewWineDto newWineDto);

}
