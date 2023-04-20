package com.ecommerce.ShopZenbe.entity;

import com.ecommerce.ShopZenbe.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.UUID;

@Entity
@Table(name="orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id", unique = true)
    private Address billingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id", unique = true)
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private OrderStatus status;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;
}
