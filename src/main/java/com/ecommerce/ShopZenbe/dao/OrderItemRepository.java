package com.ecommerce.ShopZenbe.dao;

import com.ecommerce.ShopZenbe.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
