package com.ecommerce.ShopZenbe.models.order;

import com.ecommerce.ShopZenbe.common.exceptions.ResourceNotFoundException;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Set<Order> getAllOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Customer> customer = customerRepository.findByEmail(email);
        Set<Order> orders =  customer.map(Customer::getOrders)
                .orElse(Collections.emptySet());

        return orders.stream()
                .sorted(Comparator.comparing(Order::getDateCreated).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
