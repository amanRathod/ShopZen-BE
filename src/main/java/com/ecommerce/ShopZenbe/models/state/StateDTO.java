package com.ecommerce.ShopZenbe.models.state;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
//    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;

    private Long countryId;
}
