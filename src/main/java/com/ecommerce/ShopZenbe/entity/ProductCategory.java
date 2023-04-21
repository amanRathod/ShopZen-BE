package com.ecommerce.ShopZenbe.entity;

import com.ecommerce.ShopZenbe.entity.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="product_category")
@Data
public class ProductCategory {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name", nullable = false)
    private Category categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Product> products;
}
