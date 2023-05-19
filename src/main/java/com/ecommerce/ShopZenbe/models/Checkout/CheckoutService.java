package com.ecommerce.ShopZenbe.models.Checkout;

import com.ecommerce.ShopZenbe.common.enums.OrderStatus;
import com.ecommerce.ShopZenbe.common.exceptions.DuplicateResourceException;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import com.ecommerce.ShopZenbe.models.order.Order;
import com.ecommerce.ShopZenbe.models.Checkout.dto.Purchase;
import com.ecommerce.ShopZenbe.models.Checkout.dto.PurchaseResponse;
import com.ecommerce.ShopZenbe.models.orderItem.OrderItem;
import com.ecommerce.ShopZenbe.models.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckoutService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        order.setStatus(OrderStatus.CREATED);

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        Map<UUID, Integer> productQuantities = orderItems.stream()
                .collect(Collectors.toMap(OrderItem::getProductId, OrderItem::getQuantity));
        productRepository.updateQuantities(productQuantities);

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        customerOptional.ifPresent(customer -> {
            customer.add(order);
            customerRepository.save(customer);
        });

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
