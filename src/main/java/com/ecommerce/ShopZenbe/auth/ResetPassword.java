package com.ecommerce.ShopZenbe.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    @NotNull(message = "Token cannot be empty")
    private String token;

    @NotNull(message = "Password cannot be empty")
    private String password;
}
