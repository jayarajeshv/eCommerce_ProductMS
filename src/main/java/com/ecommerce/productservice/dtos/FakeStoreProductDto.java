package com.ecommerce.productservice.dtos;

import lombok.Data;

@Data
public class FakeStoreProductDto {
    Long id;
    String title;
    Double price;
    String description;
    String category;
}
