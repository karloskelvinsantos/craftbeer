package com.beerhouse.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class BeerDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String ingredients;
    @NotBlank
    private String alcoholContent;
    @Positive
    private BigDecimal price;
    @NotBlank
    private String category;
}
