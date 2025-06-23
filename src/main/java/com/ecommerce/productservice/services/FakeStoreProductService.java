package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CategoryResponseDto;
import com.ecommerce.productservice.dtos.FakeStoreProductDto;
import com.ecommerce.productservice.dtos.ProductResponseDto;
import com.ecommerce.productservice.exceptions.NoProductsFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements IProductService {
    @Value("${fakestore-api-url}")
    private String fakestoreUrl;

    private final RestTemplate restTemplate;

    FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponseDto getProduct(Long productId) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(fakestoreUrl + productId, FakeStoreProductDto.class);
        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Sorry Product with id " + productId + " not found :-(", productId);
        }
        return fromProduct(convertFakeStoreDtoToProduct(fakeStoreProductDto));
    }

    @Override
    public List<ProductResponseDto> getAllProducts() throws NoProductsFoundException {
        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakestoreUrl, FakeStoreProductDto[].class);
        if (fakeStoreProductDtos == null) {
            throw new NoProductsFoundException("No Products Found");
        }
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            products.add(convertFakeStoreDtoToProduct(fakeStoreProductDto));
        }
        return fromProducts(products);
    }

    @Override
    public ProductResponseDto createProduct(String title, Double price, String description, String category) {
        return null;
    }


    @Override
    public ProductResponseDto updateProduct(Long productId, String title, Double price, String description, String category) {
        return null;
    }

    @Override
    public String deleteProduct(Long productId) {
        return null;
    }

    @Override
    public Page<ProductResponseDto> getProductsByTitle(String title, int pageNumber, int pageSize){
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
