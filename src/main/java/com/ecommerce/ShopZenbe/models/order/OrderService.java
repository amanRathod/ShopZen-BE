package com.ecommerce.ShopZenbe.models.order;

import com.ecommerce.ShopZenbe.common.exceptions.DuplicateResourceException;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Set<Order> getAllOrders() {
        // get all orders placed by current user (customer) from the database and return them
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // check if this is an existing customer
        String email = authentication.getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new DuplicateResourceException(
                        "Email already taken!", new Throwable("Please try with another email!")));

        return customer.getOrders();
    }
}
