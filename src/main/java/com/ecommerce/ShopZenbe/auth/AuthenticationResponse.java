package com.ecommerce.ShopZenbe.auth;

import com.ecommerce.ShopZenbe.models.customer.dto.CustomerDTO;

public record AuthenticationResponse (
        String accessToken,
        CustomerDTO customerDTO){
}