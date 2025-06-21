package com.ecommerce.productservice.exceptions;

public class ProductAlreadyExistsException extends Throwable {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
