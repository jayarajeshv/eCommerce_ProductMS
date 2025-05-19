package com.ecommerce.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundException extends Exception {
    private Long productId;

    public ProductNotFoundException(String message, Long productid) {
        super(message);
        this.productId = productid;
    }
}
