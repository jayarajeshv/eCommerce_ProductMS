package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.exceptions.CategoryAlreadyExistsException;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.InsufficientDetailsException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) throws InsufficientDetailsException, CategoryNotFoundException {
        Category category = categoryService.findCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) throws CategoryAlreadyExistsException, InsufficientDetailsException {
        Category createdCategory = categoryService.createCategory(category.getTitle());
        return ResponseEntity.status(201).body(createdCategory);
    }


    @PutMapping("/")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) throws InsufficientDetailsException, CategoryNotFoundException {
        Category updatedCategory = categoryService.updateCategory(category.getId(), category.getTitle());
        return ResponseEntity.ok(updatedCategory);
    }
}
