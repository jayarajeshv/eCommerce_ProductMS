package com.ecommerce.productservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "categories")
public class Category extends BaseModel {
    @Column(unique = true, nullable = false)
    String title;
    @OneToMany(mappedBy = "category")
    List<Product> product;
}
