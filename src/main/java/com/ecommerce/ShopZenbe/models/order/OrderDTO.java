package com.ecommerce.ShopZenbe.models.order;

import com.ecommerce.ShopZenbe.common.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class OrderDTO {
    @NotBlank(message = "Order tracking number is mandatory")
    private String orderTrackingNumber;

    @NotNull(message = "Total price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    private BigDecimal totalPrice;

    @NotNull(message = "Total quantity is mandatory")
    @Min(value = 1, message = "Total quantity must be at least 1")
    private Integer totalQuantity;

//    private Set<OrderItemDTO> orderItems;

    @NotNull(message = "Billing address is mandatory")
    private UUID billingAddressId;
//    private AddressDTO billingAddress;

    @NotNull(message = "Customer is mandatory")
    private UUID customerId;
//    private CustomerDTO customer;

    @NotNull(message = "Shipping address is mandatory")
    private UUID shippingAddressId;
//    private AddressDTO shippingAddress;

    @NotNull(message = "Order status is mandatory")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
