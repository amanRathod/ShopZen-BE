package com.ecommerce.ShopZenbe.models.customer;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);

    @EntityGraph(attributePaths = "addresses")
    Optional<Customer> findCustomerByEmail(String email);
}
