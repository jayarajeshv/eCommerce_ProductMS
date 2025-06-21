package com.ecommerce.productservice.exceptions;

public class CategoryAlreadyExistsException extends Throwable {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
