package com.ecommerce.productservice.dtos;

import lombok.Data;

@Data
public class NoProductsFoundExceptionDto {
    private String message;
    private String resolution;
}
