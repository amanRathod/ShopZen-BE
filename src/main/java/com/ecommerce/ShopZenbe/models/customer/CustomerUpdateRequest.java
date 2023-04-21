package com.ecommerce.ShopZenbe.models.customer;

public record CustomerUpdateRequest(
        String firstName,
        String lastName,
        String email
) {
}
