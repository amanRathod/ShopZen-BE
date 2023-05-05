package com.ecommerce.ShopZenbe.auth;

import com.ecommerce.ShopZenbe.models.customer.dto.CustomerDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerResponseDTO;

public record AuthenticationResponse (
        String accessToken,
        CustomerResponseDTO user){
}