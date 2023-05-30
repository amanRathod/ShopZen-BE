package com.ecommerce.ShopZenbe.models.order;

import com.ecommerce.ShopZenbe.common.exceptions.ResourceNotFoundException;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import com.ecommerce.ShopZenbe.models.order.dto.OrderDTO;

import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return modelMapper.map(order, OrderDTO.class);
    }

    public Set<Order> getAllOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Customer> customer = customerRepository.findByEmail(email);

        Set<Order> orders = customer.map(Customer::getOrders)
                .orElse(Collections.emptySet());

        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getDateCreated).reversed())
                .collect(Collectors.toList());

        return new LinkedHashSet<>(sortedOrders);
    }
}
