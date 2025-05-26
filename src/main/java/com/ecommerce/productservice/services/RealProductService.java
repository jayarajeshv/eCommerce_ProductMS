package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.NoProductsFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("RealRealProductService")
//@Primary
public class RealProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public RealProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProduct(Long productId) throws ProductNotFoundException {
//        Optional<Product> product = productRepository.findById(productId);
//        if (product.isEmpty()) {
//            throw new ProductNotFoundException("Product with id " + productId + " not found", productId);
//        }
//        return product.get();
        return productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found", productId));
    }

    @Override
    public List<Product> getAllProducts() throws NoProductsFoundException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoProductsFoundException("No products found");
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) throws CategoryNotFoundException {
        Category category = product.getCategory();
        if (category == null) {
            throw new CategoryNotFoundException("Product cannot be created without a valid category");
        }

        Optional<Category> categoryOptional = categoryRepository.findByTitle(category.getTitle());
        if (categoryOptional.isEmpty()) {
            if (category.getTitle() == null) {
                throw new CategoryNotFoundException("Category Not Found");
            }
            categoryRepository.save(category);
        } else {
            category = categoryOptional.get();
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductNotFoundException {
        productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException("Product with " + productId + "doesn't exist in the system", productId));
        productRepository.deleteById(productId);
        return "Product with id " + productId + " deleted successfully";
    }
}
