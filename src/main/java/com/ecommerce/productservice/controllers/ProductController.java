package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.exceptions.NoProductsFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(@Qualifier("FakeStoreProductService") IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() throws NoProductsFoundException {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
}
