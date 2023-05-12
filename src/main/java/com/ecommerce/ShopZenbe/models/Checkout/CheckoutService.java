package com.ecommerce.ShopZenbe.models.Checkout;

import com.ecommerce.ShopZenbe.common.enums.OrderStatus;
import com.ecommerce.ShopZenbe.common.exceptions.DuplicateResourceException;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import com.ecommerce.ShopZenbe.models.order.Order;
import com.ecommerce.ShopZenbe.models.Checkout.dto.Purchase;
import com.ecommerce.ShopZenbe.models.Checkout.dto.PurchaseResponse;
import com.ecommerce.ShopZenbe.models.orderItem.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutService {
    @Autowired
    private CustomerRepository customerRepository;
    CheckoutService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        order.setStatus(OrderStatus.CREATED);

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // check if this is an existing customer
        String email = authentication.getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new DuplicateResourceException(
                        "Email already taken!", new Throwable("Please try with another email!")));
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
