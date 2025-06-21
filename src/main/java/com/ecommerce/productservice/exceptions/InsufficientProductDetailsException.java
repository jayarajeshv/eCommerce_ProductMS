package com.ecommerce.productservice.exceptions;

public class InsufficientProductDetailsException extends Throwable {
    public InsufficientProductDetailsException(String message) {
        super(message);
    }
}
