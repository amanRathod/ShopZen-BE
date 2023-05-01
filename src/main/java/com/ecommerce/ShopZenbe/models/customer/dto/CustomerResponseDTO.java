package com.ecommerce.ShopZenbe.models.customer.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
