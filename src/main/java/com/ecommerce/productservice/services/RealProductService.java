package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("RealProductService")
@Primary
public class RealProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public RealProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Cacheable(value = "Products", key = "'PRODUCT_' + #productId")
    public Product getProduct(Long productId) throws ProductNotFoundException {
//        Optional<Product> product = productRepository.findById(productId);
//        if (product.isEmpty()) {
//            throw new ProductNotFoundException("Product with id " + productId + " not found", productId);
//        }
//        return product.get();
        return productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found", productId));
    }

    @Override
    @Cacheable(value = "AllProducts")
    public List<Product> getAllProducts() throws NoProductsFoundException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoProductsFoundException("No products found");
        }
        return products;
    }

    @Override
    public Product createProduct(String title, Double price, String description, String category) throws CategoryNotFoundException, InsufficientProductDetailsException, ProductAlreadyExistsException, ProductCategoryMandatoryException {
        if (title.isEmpty() || price == null || description == null) {
            throw new InsufficientProductDetailsException("Product details cannot be null or empty");
        }
        if (category.isEmpty()) {
            throw new ProductCategoryMandatoryException("Product cannot be created without a valid category");
        }

        Optional<Product> existingProduct = productRepository.findByTitleAndDescription(title, description);
        if (existingProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product with title '" + title + "' and description '" + description + "' already exists.");
        }

        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        Optional<Category> categoryOptional = categoryRepository.findByTitle(category);
        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException("Category with title '" + category + "' not found. Please create the category first.");
        }
        product.setCategory(categoryOptional.get());
        return productRepository.save(product);
    }

    @Override
    @CachePut(value = "Products", key = "'PRODUCT_' + #productId")
    public Product updateProduct(Long productId, String title, Double price, String description, String category) throws ProductNotFoundException, InsufficientProductDetailsException, ProductCategoryMandatoryException, CategoryNotFoundException {
        if (title.isEmpty() || price == null || description == null) {
            throw new InsufficientProductDetailsException("Product details cannot be null or empty");
        }
        if (category.isEmpty()) {
            throw new ProductCategoryMandatoryException("Product cannot be created without a valid category");
        }
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + productId + " not found", productId);
        }
        Product product = optionalProduct.get();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        Optional<Category> categoryOptional = categoryRepository.findByTitle(category);
        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException("Category with title '" + category + "' not found. Please create the category first.");
        }
        product.setCategory(categoryOptional.get());
        return productRepository.save(product);
    }

    @Override
    @CacheEvict(value = "Products", key = "'PRODUCT_' + #productId")
    public String deleteProduct(Long productId) throws ProductNotFoundException {
        productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException("Product with " + productId + "doesn't exist in the system", productId));
        productRepository.deleteById(productId);
        return "Product with id " + productId + " deleted successfully";
    }
}
