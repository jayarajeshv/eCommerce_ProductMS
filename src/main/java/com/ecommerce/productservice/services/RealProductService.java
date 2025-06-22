package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CategoryResponseDto;
import com.ecommerce.productservice.dtos.ProductResponseDto;
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

import java.util.ArrayList;
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
    public ProductResponseDto getProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found", productId));
        return fromProduct(product);
    }

    @Override
    @Cacheable(value = "AllProducts")
    public List<ProductResponseDto> getAllProducts() throws NoProductsFoundException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoProductsFoundException("No products found");
        }
        return fromProducts(products);
    }

    @Override
    public ProductResponseDto createProduct(String title, Double price, String description, String category) throws CategoryNotFoundException, InsufficientDetailsException, ProductAlreadyExistsException, ProductCategoryMandatoryException {
        if (title.isEmpty() || price == null || description == null) {
            throw new InsufficientDetailsException("Product details cannot be null or empty");
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
        return fromProduct(productRepository.save(product));
    }

    @Override
    @CachePut(value = "Products", key = "'PRODUCT_' + #productId")
    public ProductResponseDto updateProduct(Long productId, String title, Double price, String description, String category) throws ProductNotFoundException, InsufficientDetailsException, ProductCategoryMandatoryException, CategoryNotFoundException {
        if (productId == null || title.isEmpty() || price == null || description == null) {
            throw new InsufficientDetailsException("Product details cannot be null or empty");
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
        return fromProduct(productRepository.save(product));
    }

    @Override
    @CacheEvict(value = "Products", key = "'PRODUCT_' + #productId")
    public String deleteProduct(Long productId) throws ProductNotFoundException {
        productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException("Product with " + productId + "doesn't exist in the system", productId));
        productRepository.deleteById(productId);
        return "Product with id " + productId + " deleted successfully";
    }

    private ProductResponseDto fromProduct(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(product.getCategory().getId());
        categoryResponseDto.setName(product.getCategory().getTitle());
        productResponseDto.setCategory(categoryResponseDto);
        return productResponseDto;
    }

    private List<ProductResponseDto> fromProducts(List<Product> allProducts) {
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : allProducts) {
            productResponseDtos.add(fromProduct(product));
        }
        return productResponseDtos;
    }
}
