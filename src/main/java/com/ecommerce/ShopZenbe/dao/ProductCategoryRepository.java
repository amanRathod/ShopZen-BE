package com.ecommerce.ShopZenbe.dao;

import com.ecommerce.ShopZenbe.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
