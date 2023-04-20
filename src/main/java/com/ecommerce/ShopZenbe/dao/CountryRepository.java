package com.ecommerce.ShopZenbe.dao;

import com.ecommerce.ShopZenbe.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Short> {
}
