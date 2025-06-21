package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.CategoryAlreadyExistsException;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.InsufficientDetailsException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryById(Long categoryId) throws CategoryNotFoundException, InsufficientDetailsException {
        if (categoryId == null) {
            throw new InsufficientDetailsException("Category ID cannot be null");
        }
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category with id " + categoryId + " not found"));
    }

    @Override
    public Category createCategory(String title) throws CategoryAlreadyExistsException, InsufficientDetailsException {
        if (title == null || title.isEmpty()) {
            throw new InsufficientDetailsException("Category name cannot be null or empty");
        }
        Optional<Category> existingCategory = categoryRepository.findCategoryByTitle(title);
        if (existingCategory.isPresent()) {
            throw new CategoryAlreadyExistsException("Category with name " + title + " already exists");
        }
        Category category = new Category();
        category.setTitle(title);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, String title) throws CategoryNotFoundException, InsufficientDetailsException {
        if (categoryId == null || title.isEmpty()) {
            throw new InsufficientDetailsException("Category details cannot be null or empty");
        }
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
        }
        Category category = optionalCategory.get();
        category.setTitle(title);
        return categoryRepository.save(category);
    }
}
