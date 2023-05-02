package com.ecommerce.ShopZenbe.models.Checkout.dto;

import com.ecommerce.ShopZenbe.models.address.Address;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.order.Order;
import com.ecommerce.ShopZenbe.models.orderItem.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
