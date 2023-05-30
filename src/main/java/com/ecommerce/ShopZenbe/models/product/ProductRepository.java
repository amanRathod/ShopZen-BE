package com.ecommerce.ShopZenbe.models.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Map;
import java.util.UUID;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

//    @Modifying
//    @Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.id IN :productIds")
//    void updateQuantities(@Param("productIds") Map<UUID, Integer> productQuantities);

    @Modifying
    @Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.id = :productId")
    void updateQuantity(@Param("productId") UUID productId, @Param("quantity") Integer quantity);

    @Modifying
    default void updateQuantities(Map<UUID, Integer> productQuantities) {
        for (Map.Entry<UUID, Integer> entry : productQuantities.entrySet()) {
            UUID productId = entry.getKey();
            Integer quantity = entry.getValue();
            updateQuantity(productId, quantity);
        }
    }
}
