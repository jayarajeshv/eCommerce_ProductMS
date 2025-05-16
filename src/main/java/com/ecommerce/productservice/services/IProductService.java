package com.ecommerce.productservice.services;

import com.ecommerce.productservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProduct(Long productId);

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long productId);

    //Default method used to reserve method for service class
    default Product testMethod() {
        return new Product();
    }
}
