package com.ecommerce.ShopZenbe.models.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateCustomerDTO(
        @NotBlank()
        String firstName,

        @NotBlank()
        String lastName,
        @Email()
        String email
) {
}
