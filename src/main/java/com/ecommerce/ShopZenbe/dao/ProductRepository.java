package com.ecommerce.ShopZenbe.dao;

import com.ecommerce.ShopZenbe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.UUID;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
