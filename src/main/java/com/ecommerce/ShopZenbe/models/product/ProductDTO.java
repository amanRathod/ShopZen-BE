package com.ecommerce.ShopZenbe.models.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "SKU is required")
    private String sku;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Active status is required")
    private Boolean active = true;

    @NotNull(message = "Units in stock is required")
    @Min(value = 0, message = "Units in stock must be greater than or equal to 0")
    private Integer unitsInStock;

    @NotNull(message = "Product Category is required")
    private UUID categoryID;
}
