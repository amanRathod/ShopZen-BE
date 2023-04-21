package com.ecommerce.ShopZenbe.models.orderItem;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
public class OrderItemDTO {
    @NotEmpty(message = "Image URL cannot be empty")
    private String imageUrl;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than or equal to 0.01")
    private BigDecimal unitPrice;

    @NotNull(message = "Order is mandatory")
    private UUID orderId;
//    private OrderDTO order;

    @NotNull(message = "product is mandatory")
    private UUID productId;
//    private ProductDTO product;
}
