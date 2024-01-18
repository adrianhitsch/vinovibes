package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.dtos.WineDto;
import com.vinovibes.vinoapi.entities.Wine;
import com.vinovibes.vinoapi.exceptions.AppException;
import com.vinovibes.vinoapi.mappers.ProductMapper;
import com.vinovibes.vinoapi.repositories.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final WineRepository wineRepository;
    private final ProductMapper productMapper;

    public WineDto getProductById(Long id) {
        Wine wine = wineRepository.findById(id).orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));
        return productMapper.toProductDto(wine);
    }
}
