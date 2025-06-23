package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.dtos.ProductResponseDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.services.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(@Qualifier("RealProductService") IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long productId) throws ProductNotFoundException {
        ProductResponseDto productResponseDto = productService.getProduct(productId);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() throws NoProductsFoundException {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) throws CategoryNotFoundException, InsufficientDetailsException, ProductAlreadyExistsException, ProductCategoryMandatoryException {

        ProductResponseDto productResponseDto = productService.createProduct(productRequestDto.getTitle(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription(),
                productRequestDto.getCategory());

        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto productRequestDto) throws ProductNotFoundException, InsufficientDetailsException, ProductCategoryMandatoryException, CategoryNotFoundException {
        ProductResponseDto productResponseDto = productService.updateProduct(productRequestDto.getId(),
                productRequestDto.getTitle(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription(),
                productRequestDto.getCategory());
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/title/{title}/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByTitle(@PathVariable("title") String title,
                                                                       @PathVariable("pageNumber") int pageNumber,
                                                                       @PathVariable("pageSize") int pageSize) throws NoProductsFoundException {
        return new ResponseEntity<>(productService.getProductsByTitle(title, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Product Service is running";
    }
}
