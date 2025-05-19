package com.ecommerce.productservice.dtos;

import lombok.Data;

@Data
public class ProductNotFoundExceptionDto {
    private Long productId;
    private String message;
    private String resolution;
}
