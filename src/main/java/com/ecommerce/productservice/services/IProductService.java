package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.ProductResponseDto;
import com.ecommerce.productservice.exceptions.*;

import java.util.List;

public interface IProductService {
    ProductResponseDto getProduct(Long productId) throws ProductNotFoundException;

    List<ProductResponseDto> getAllProducts() throws NoProductsFoundException;

    ProductResponseDto createProduct(String title, Double price, String description, String category) throws CategoryNotFoundException, InsufficientDetailsException, ProductAlreadyExistsException, ProductCategoryMandatoryException;

    ProductResponseDto updateProduct(Long productId, String title, Double price, String description, String category) throws ProductNotFoundException, InsufficientDetailsException, ProductCategoryMandatoryException, CategoryNotFoundException;

    String deleteProduct(Long productId) throws ProductNotFoundException;
}
