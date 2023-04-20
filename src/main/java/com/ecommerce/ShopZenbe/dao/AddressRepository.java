package com.ecommerce.ShopZenbe.dao;

import com.ecommerce.ShopZenbe.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
