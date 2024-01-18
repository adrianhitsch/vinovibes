package com.vinovibes.vinoapi.mappers;
import com.vinovibes.vinoapi.dtos.WineDto;
import com.vinovibes.vinoapi.entities.Wine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    public WineDto toProductDto(Wine wine);

}
