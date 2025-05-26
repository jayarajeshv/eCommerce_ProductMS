package com.ecommerce.productservice.exceptionHandlers;

import com.ecommerce.productservice.dtos.NoProductsFoundExceptionDto;
import com.ecommerce.productservice.dtos.ProductNotFoundExceptionDto;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.NoProductsFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductServiceExceptionHandler {
    @ExceptionHandler(NoProductsFoundException.class)
    public ResponseEntity<NoProductsFoundExceptionDto> NoProductsFoundExceptionHandler(NoProductsFoundException exception) {
        NoProductsFoundExceptionDto dto = new NoProductsFoundExceptionDto();
        dto.setMessage(exception.getMessage());
        dto.setResolution("No Products Available currently, Please try again later.");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundExceptionDto> ProductNotFoundExceptionHandler(ProductNotFoundException exception) {
        ProductNotFoundExceptionDto dto = new ProductNotFoundExceptionDto();
        dto.setProductId(exception.getProductId());
        dto.setMessage(exception.getMessage());
        dto.setResolution("Product " + exception.getProductId() + " is unavailable currently.");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> ProductNotFoundExceptionHandler(CategoryNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
