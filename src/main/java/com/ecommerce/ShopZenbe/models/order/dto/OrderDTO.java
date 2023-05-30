package com.ecommerce.ShopZenbe.models.order.dto;

import com.ecommerce.ShopZenbe.common.enums.OrderStatus;
import com.ecommerce.ShopZenbe.models.address.dto.AddressDTO;
import com.ecommerce.ShopZenbe.models.orderItem.OrderItem;
import com.ecommerce.ShopZenbe.common.enums.PaymentOption;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String id;
    private String orderTrackingNumber;

    @NotNull(message = "Total price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    private BigDecimal totalPrice;

    @NotNull(message = "Total quantity is mandatory")
    @Min(value = 1, message = "Total quantity must be at least 1")
    private Integer totalQuantity;

    @NotNull(message = "Payment method is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name="payment_method", nullable = false)
    private PaymentOption paymentMethod;

    private Set<OrderItem> orderItems;
    private AddressDTO billingAddress;
    private AddressDTO shippingAddress;
    private OrderStatus status;
    private Timestamp dateCreated;
    private Timestamp lastUpdated;
}
