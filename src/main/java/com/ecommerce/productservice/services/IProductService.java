package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.NoProductsFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts() throws NoProductsFoundException;

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long productId);

    //Default method used to reserve method for service class
    default Product testMethod() {
        return new Product();
    }
}
