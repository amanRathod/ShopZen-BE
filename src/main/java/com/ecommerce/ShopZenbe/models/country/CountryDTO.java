package com.ecommerce.ShopZenbe.models.country;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {
//    private Short id;

    @NotBlank(message = "Code is mandatory")
    @Size(min = 2, max = 2, message = "Code must be 2 characters long")
    private String code;

    @NotBlank(message = "Name is mandatory")
    private String name;
}
