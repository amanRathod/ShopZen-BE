package com.ecommerce.ShopZenbe.models.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findAllByCustomerId(UUID customerId);
}
