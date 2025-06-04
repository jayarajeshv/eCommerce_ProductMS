package com.ecommerce.productservice.dtos;

import com.ecommerce.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private Category category;
}
