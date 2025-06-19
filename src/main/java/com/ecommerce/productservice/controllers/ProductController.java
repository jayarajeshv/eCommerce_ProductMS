package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.dtos.ProductResponseDto;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.NoProductsFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(@Qualifier("FakeStoreProductService") IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return new ResponseEntity<>(fromProduct(productService.getProduct(productId)), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() throws NoProductsFoundException {
        return new ResponseEntity<>(fromProducts(productService.getAllProducts()), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) throws CategoryNotFoundException {
        return new ResponseEntity<>(fromProduct(productService.createProduct(toProduct(productRequestDto))), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }

    private ProductResponseDto fromProduct(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategory(product.getCategory());
        return productResponseDto;
    }

    private List<ProductResponseDto> fromProducts(List<Product> allProducts) {
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : allProducts) {
            productResponseDtos.add(fromProduct(product));
        }
        return productResponseDtos;
    }

    private Product toProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setTitle(productRequestDto.getTitle());
        product.setPrice(productRequestDto.getPrice());
        product.setDescription(productRequestDto.getDescription());
        Category category = new Category();
        category.setTitle(productRequestDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
