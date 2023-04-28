package com.ecommerce.ShopZenbe.models.product.dto;

import java.math.BigDecimal;

public record UpdateProductDTO(
        String sku,
        String name,
        String description,
        BigDecimal price,
        String image,
        Boolean active,
        Integer stock
) {
}
