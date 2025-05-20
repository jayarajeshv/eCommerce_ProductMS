package com.ecommerce.productservice.repositories;

import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByTitleIgnoreCase(String title);

    List<Product> findProductsByCategory(Category category);

    List<Product> findProductsByCategoryAndTitle(Category category, String title);

    List<Product> findProductsByCategoryBetween(Category category1, Category category2);

    List<Product> findAllByCategory_Id(Long categoryId);

    List<Product> findByPriceBetween(Double lower, Double higher);

    List<Product> findAllByCategory_Title(String title);

    Optional<Product> findProductOneByTitle(String title);
}
