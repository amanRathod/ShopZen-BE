package com.ecommerce.ShopZenbe.models.address.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String id;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Zip code is required")
    private String zipCode;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private Boolean isBilling;
    private Boolean isShipping;
}
