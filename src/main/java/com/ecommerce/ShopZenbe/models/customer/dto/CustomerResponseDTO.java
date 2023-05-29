package com.ecommerce.ShopZenbe.models.customer.dto;

import com.ecommerce.ShopZenbe.models.address.dto.AddressDTO;

import lombok.*;

import java.util.List;
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
    private String image;
    private AddressDTO primaryAddress;
    private List<AddressDTO> addresses;
}
