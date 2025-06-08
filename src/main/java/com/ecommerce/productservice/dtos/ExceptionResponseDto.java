package com.ecommerce.productservice.dtos;

import lombok.Data;

@Data
public class ExceptionResponseDto {
    private String message;
    private String resolution;
}
