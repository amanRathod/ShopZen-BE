package com.ecommerce.ShopZenbe.models.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
