package com.ecommerce.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "products")
public class Product extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private Double price;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn
    @JsonBackReference
    private Category category;
}
