package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.CategoryResponseDto;
import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.dtos.ProductResponseDto;
import com.ecommerce.productservice.exceptions.*;
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

    public ProductController(@Qualifier("RealProductService") IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long productId
//            , @RequestHeader("Token") String tokenValue
    ) throws ProductNotFoundException {
//        UserResponseDto userResponseDto = authCommons.validateToken(tokenValue);
        Product product = productService.getProduct(productId);
        return new ResponseEntity<>(fromProduct(product), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() throws NoProductsFoundException {
        return new ResponseEntity<>(fromProducts(productService.getAllProducts()), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) throws CategoryNotFoundException, InsufficientDetailsException, ProductAlreadyExistsException, ProductCategoryMandatoryException {

        Product product = productService.createProduct(productRequestDto.getTitle(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription(),
                productRequestDto.getCategory());

        return new ResponseEntity<>(fromProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto productRequestDto) throws ProductNotFoundException, InsufficientDetailsException, ProductCategoryMandatoryException, CategoryNotFoundException {
        Product product = productService.updateProduct(productRequestDto.getId(),
                productRequestDto.getTitle(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription(),
                productRequestDto.getCategory());
        return new ResponseEntity<>(fromProduct(product), HttpStatus.OK);
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
