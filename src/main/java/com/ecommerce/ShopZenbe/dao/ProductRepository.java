package com.ecommerce.ShopZenbe.dao;

import com.ecommerce.ShopZenbe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.UUID;

@CrossOrigin("http://localhost:3000")
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
