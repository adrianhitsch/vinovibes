package com.vinovibes.vinoapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewWineDto {

        private String name;
        private double rating;
        private String description;
        private String producer;
        private int vintage;
}
