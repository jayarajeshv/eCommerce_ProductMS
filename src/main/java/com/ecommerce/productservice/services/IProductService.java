package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts() throws NoProductsFoundException;

    Product createProduct(String title, Double price, String description, String category) throws CategoryNotFoundException, InsufficientDetailsException, ProductAlreadyExistsException, ProductCategoryMandatoryException;

    Product updateProduct(Long productId, String title, Double price, String description, String category) throws ProductNotFoundException, InsufficientDetailsException, ProductCategoryMandatoryException, CategoryNotFoundException;

    String deleteProduct(Long productId) throws ProductNotFoundException;
}
