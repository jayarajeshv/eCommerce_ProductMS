package com.ecommerce.productservice.exceptions;

public class InsufficientDetailsException extends Throwable {
    public InsufficientDetailsException(String message) {
        super(message);
    }
}
