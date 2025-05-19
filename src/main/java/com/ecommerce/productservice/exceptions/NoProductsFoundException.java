package com.ecommerce.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoProductsFoundException extends Exception {
    public NoProductsFoundException(String message) {
        super(message);
    }
}
