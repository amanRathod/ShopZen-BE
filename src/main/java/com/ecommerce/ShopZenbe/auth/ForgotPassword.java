package com.ecommerce.ShopZenbe.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPassword {
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;
}
