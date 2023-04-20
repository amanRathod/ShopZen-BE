package com.ecommerce.ShopZenbe.dao;

import com.ecommerce.ShopZenbe.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
