package com.ecommerce.productservice.exceptions;

public class UnAuthorizedAccessException extends Exception {
    public UnAuthorizedAccessException(String message) {
        super(message);
    }
}
