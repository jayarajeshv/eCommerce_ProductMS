package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.CategoryAlreadyExistsException;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.InsufficientDetailsException;
import com.ecommerce.productservice.models.Category;

public interface ICategoryService {
    Category findCategoryById(Long categoryId) throws CategoryNotFoundException, InsufficientDetailsException;
    Category createCategory(String title) throws CategoryNotFoundException, CategoryAlreadyExistsException, InsufficientDetailsException;
    Category updateCategory(Long categoryId, String title) throws CategoryNotFoundException, InsufficientDetailsException;
}
