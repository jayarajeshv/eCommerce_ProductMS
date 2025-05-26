package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.NoProductsFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts() throws NoProductsFoundException;

    Product createProduct(Product product) throws CategoryNotFoundException;

    Product updateProduct(Product product);

    String deleteProduct(Long productId) throws ProductNotFoundException;

    //Default method used to reserve method for service class
    default Product testMethod() {
        return new Product();
    }
}
