package com.ecommerce.ShopZenbe.models.address.dto;

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
    private String city;
    private String state;
    private String country;
    private String street;
    private String zipCode;
    private String phone;
    private String fullName;
    private Boolean isBilling;
    private Boolean isShipping;
}
