package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.FakeStoreProductDto;
import com.ecommerce.productservice.exceptions.NoProductsFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements IProductService {
    @Value("${fakestore-api-url}")
    private String fakestoreUrl;

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProduct(Long productId) throws ProductNotFoundException {

        try {
            Object value = redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + productId);
            Product product = null;
            if (value != null) {
                if (value instanceof Product) {
                    product = (Product) value;
                } else if (value instanceof LinkedHashMap) {
                    ObjectMapper mapper = new ObjectMapper();
                    product = mapper.convertValue(value, Product.class);
                }
                return product;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error accessing Redis cache: " + e.getMessage(), e);
        }

        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(fakestoreUrl + productId, FakeStoreProductDto.class);
        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Sorry Product with id " + productId + " not found :-(", productId);
        }

        Product product = convertFakeStoreDtoToProduct(fakeStoreProductDto);
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws NoProductsFoundException {
        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakestoreUrl, FakeStoreProductDto[].class);
        if (fakeStoreProductDtos == null) {
            throw new NoProductsFoundException("No Products Found");
        }
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            products.add(convertFakeStoreDtoToProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public String deleteProduct(Long productId) {
        return null;
    }

    private Product convertFakeStoreDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
        if (fakeStoreProductDto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
