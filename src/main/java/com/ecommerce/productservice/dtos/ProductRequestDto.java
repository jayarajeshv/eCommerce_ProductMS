package com.ecommerce.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
}
