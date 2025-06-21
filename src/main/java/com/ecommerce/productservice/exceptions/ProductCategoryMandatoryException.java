package com.ecommerce.productservice.exceptions;

public class ProductCategoryMandatoryException extends Throwable {
    public ProductCategoryMandatoryException(String message) {
        super(message);
    }
}
